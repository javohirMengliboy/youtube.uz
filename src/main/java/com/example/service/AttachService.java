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

    public AttachDTO upload(MultipartFile file) {
        String pathFolder = getYMD();
        File folder = new File(folderName + "/" + pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String key = UUID.randomUUID().toString();
        String extension = getExtension(file.getOriginalFilename());
        System.out.println(extension);
        System.out.println(file.getContentType());
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/s" + key + "." + extension);
            Files.write(path, bytes);
            AttachEntity attachEntity = new AttachEntity();
            attachEntity.setId(Integer.valueOf(key));
            attachEntity.setSize(file.getSize());
            attachEntity.setExtension(extension);
            attachEntity.setOrigin_name(file.getOriginalFilename());
            attachEntity.setPath(folderName + "/" + pathFolder + "/" + key + "." + extension);
            attachRepository.save(attachEntity);
            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(Integer.valueOf(key));
            attachDTO.setPath(attachUrl);
            attachDTO.setSize(file.getSize());
            attachDTO.setExtension(extension);
            attachDTO.setUrl(getUrl(key));
            attachDTO.setOrigin_name(file.getOriginalFilename());
            return attachDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getById(Integer id){
        AttachEntity attachEntity = get(id);

        try {
            String url = attachEntity.getPath();
            BufferedImage image = ImageIO.read(new File(url));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, attachEntity.getExtension(), baos);
            baos.flush();
            byte[] bytes = baos.toByteArray();
            baos.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }



    public byte[] downloadImage(String fileName) throws IOException{
        Optional<AttachDTO> imageObject = attachRepository.findAllByOrigin_name(fileName);
        String fullPath = imageObject.get().getPath();
        return Files.readAllBytes(new File(fullPath).toPath());
    }



    public PageImpl<AttachDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AttachEntity> pageObj = attachRepository.findAll(pageable);
        List<AttachDTO> dtoList = pageObj.getContent().stream().map(s -> {
            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(s.getId());
            attachDTO.setPath(s.getPath());
            attachDTO.setSize(s.getSize());
            attachDTO.setExtension(s.getExtension());
            attachDTO.setOrigin_name(s.getOrigin_name());
            return attachDTO;
        }).toList();
        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }


    public Boolean deleteById(Integer id) {
        boolean t = false;
        AttachEntity attachEntity = get(id);
        attachRepository.deleteById(id);
        File file = new File(attachEntity.getPath());
        if (file.exists()) {
            t = file.delete();
        }
        return t;
    }


    private String getUrl(String id) {
        return attachUrl + "/open/" + id + "/img";
    }



    private AttachEntity get(Integer id) {
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
}
