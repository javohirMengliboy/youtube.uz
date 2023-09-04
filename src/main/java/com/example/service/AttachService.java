package com.example.service;

import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import com.example.exaption.ItemNotFoundException;
import com.example.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachService {

    @Autowired
    private AttachRepository attachRepository;

    @Value("${attach.folder.name}")
    private String folderName;

    @Value("${attach.url}")
    private String attachUrl;

    // 1. Attach upload
    public AttachDTO upload(MultipartFile file) {
        String pathFolder = getYMD();
        File folder = new File(folderName + "/" + pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String key = UUID.randomUUID().toString();
        String extension = getExtension(file.getOriginalFilename());
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + key + "." + extension );
            Files.write(path, bytes);
//            String duration = getDuration(file, path);
            AttachEntity attachEntity = new AttachEntity();
            attachEntity.setId(key+ "." + extension);
            attachEntity.setSize(file.getSize());
            attachEntity.setExtension(extension);
            attachEntity.setOriginalName(file.getOriginalFilename());
            attachEntity.setPath(folderName + "/" + pathFolder);
//            attachEntity.setDuration(duration);
            attachRepository.save(attachEntity);
            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(attachEntity.getId());
            attachDTO.setPath(attachEntity.getPath());
            attachDTO.setSize(attachEntity.getSize());
            attachDTO.setExtension(attachEntity.getExtension());
            attachDTO.setDuration(attachEntity.getDuration());
            if (extension.equals("mp4")){
                attachDTO.setUrl(getUrl(key + "." + extension + "/general"));
            }else {
                attachDTO.setUrl(getUrl(key + "." + extension+"/img"));
            }
            attachDTO.setOrigin_name(attachEntity.getOriginalName());
            return attachDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 2. Open by id
    public byte[] getById(String id){
        AttachEntity attachEntity = get(id);
        try {
            String url = attachEntity.getPath();
            BufferedImage image = ImageIO.read(new File(url+"/"+id));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, attachEntity.getExtension(), baos);
            baos.flush();
            byte[] bytes = baos.toByteArray();
            baos.close();
            return bytes;
        } catch (IOException e) {
            return new byte[0];
        }
    }

    // 2. Open General
    public byte[] loadByIdGeneral(String id) {
        AttachEntity entity = get(id);
        try {
            String url = entity.getPath() + "/" + id;
            File file = new File(url);
            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    // 3. Download Attach
    public byte[] downloadImage(String fileName) throws IOException{
        Optional<AttachEntity> imageObject = attachRepository.getByName(fileName);
        String fullPath = imageObject.get().getPath()+"/"+imageObject.get().getId();
        return Files.readAllBytes(new File(fullPath).toPath());
    }

    // 4. Attach pagination
    public PageImpl<AttachDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AttachEntity> pageObj = attachRepository.getAttachPagination(pageable);
        List<AttachDTO> dtoList = pageObj.getContent().stream().map(s -> {
            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(s.getId());
            attachDTO.setPath(s.getPath());
            attachDTO.setSize(s.getSize());
            attachDTO.setExtension(s.getExtension());
            attachDTO.setOrigin_name(s.getOriginalName());
            return attachDTO;
        }).toList();
        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    // 5. Delete Attach
    public Boolean deleteById(String id) {
        boolean t = false;
        AttachEntity attachEntity = get(id);
        attachRepository.deleteById(id);
        File file = new File(attachEntity.getPath());
        if (file.exists()) {
            t = file.delete();
        }
        return t;
    }

    public String getUrl(String id) {
        return attachUrl + "/open/" + id;
    }

    private AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Attach not found!"));
    }

    private String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    private String getYMD() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    private String getDuration(MultipartFile file, Path path){
        try {
            Path tempFile = Files.createTempFile("temp", file.getOriginalFilename());
            file.transferTo(tempFile.toFile());
            System.out.println("path -> ");
//            file.transferTo(path.toFile());
            String[] command = {"ffmpeg", "-i", tempFile.toString()};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            String output = new String(process.getInputStream().readAllBytes());
            String durationLine = output.lines().filter(line -> line.contains("Duration:"))
                    .findFirst().orElse("");

            String[] durationParts = durationLine.split(",")[0].split("Duration:")[1].trim().split(":");
            int hours = Integer.parseInt(durationParts[0]);
            int minutes = Integer.parseInt(durationParts[1]);
            float seconds = Integer.parseInt(durationParts[2]);

            float totalDurationInSeconds = hours * 3600 + minutes * 60 + seconds;
            return "Duration: " + totalDurationInSeconds + " seconds";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
