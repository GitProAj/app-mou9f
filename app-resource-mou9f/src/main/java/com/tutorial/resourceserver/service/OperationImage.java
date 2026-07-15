package com.tutorial.resourceserver.service;

import com.tutorial.resourceserver.dto.MessageDto;
import com.tutorial.resourceserver.entity.ClientMou9f;
import com.tutorial.resourceserver.entity.ImageClient;
import com.tutorial.resourceserver.entity.VideoClient;
import com.tutorial.resourceserver.repository.ClientMou9fRepository;
import com.tutorial.resourceserver.repository.ImageRepository;
import com.tutorial.resourceserver.service.athentification.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OperationImage {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientMou9fRepository clientMou9fRepository;

        public ResponseEntity<MessageDto> uploadFile(String title, String description, MultipartFile file) {
            try {
                String username = userService.getCurrentUsername();
                long idClient = clientMou9fRepository.findByUsername(username).get().getId();

                ImageClient fileImage = new ImageClient();
                fileImage.setIdClient(idClient);
                fileImage.setTitle(title);
                fileImage.setDescription(description);
                fileImage.setBytesImage(file.getBytes());
                fileImage.setSizeImage(file.getSize());
                fileImage.setContentTypeImage(file.getContentType());
                fileImage.setDateUpload(LocalDateTime.now());
                imageRepository.save(fileImage);
                return new ResponseEntity<>(new MessageDto("image added to data base",""), HttpStatus.CREATED);

            } catch (IOException e) {
                return new ResponseEntity<>(new MessageDto("","error to added image"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    public List<ImageClient> getImageById(Long id) {
        return imageRepository.findByIdClient(id);
    }
    public List<ImageClient> getAllImageByCurrentUser(){
            long clientId = clientMou9fRepository.findByUsername(
                                    userService.getCurrentUsername()
                            ).get().getId();
            return imageRepository.findByIdClient(clientId);
    }

    public MessageDto deleteImage(long id) {
        Optional<ImageClient> videoOpt = imageRepository.findById(id);
        try {
            imageRepository.deleteById(id);
            return new MessageDto("image execute deleted", "");
        } catch (Exception e) {
            return new MessageDto("", "image not deleted");
        }
    }
}
