package com.aitnacer.LabXpert.service.impl;

import com.aitnacer.LabXpert.dtos.UtilisateurDto;
import com.aitnacer.LabXpert.entity.Utilisateur;
import com.aitnacer.LabXpert.exception.common.ApiException;
import com.aitnacer.LabXpert.repository.UserRepository;
import com.aitnacer.LabXpert.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;



    public List<UtilisateurDto> getAllUtilisateur(){
        List<Utilisateur> utilisateurs = userRepository.findByDeletedFalse();
        return  utilisateurs.stream().map(administrateur -> modelMapper.map(administrateur, UtilisateurDto.class)).collect(Collectors.toList());
    }
    public UtilisateurDto getUtilisateurById(Long id)  {
        Utilisateur utilisateur = userRepository.findByIdAndDeletedFalse(id).orElseThrow(new ApiException(String.format("No User fond for this   %s",id), HttpStatus.BAD_REQUEST));
        return modelMapper.map(utilisateur, UtilisateurDto.class);
    }
    public UtilisateurDto createUtilisateur(UtilisateurDto utilisateurDto){
        // TODO verification for administrateur
        Utilisateur utilisateur = modelMapper.map(utilisateurDto, Utilisateur.class);
        System.out.println(utilisateur);
        String pw= utilisateur.getPassword();
        utilisateur.setPassword(passwordEncoder.encode(pw));
        Utilisateur administrateurSaved  = userRepository.save(utilisateur);
        return  modelMapper.map(administrateurSaved, UtilisateurDto.class);

    }
    public UtilisateurDto updateUtilisateur(Long id , UtilisateurDto administrateurDto){
        //TODO trow exption not found and validation
        Utilisateur existingdUtilisateur = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ApiException("user not found with  ", id));
        //TODO validation
        existingdUtilisateur.setPrenom(administrateurDto.getPrenom());
        existingdUtilisateur.setNom(administrateurDto.getNom());
        existingdUtilisateur.setAdresse(administrateurDto.getAdresse());
        existingdUtilisateur.setUserName(administrateurDto.getUserName());
        existingdUtilisateur.setPassword(administrateurDto.getPassword());
        existingdUtilisateur.setTelephone(administrateurDto.getTelephone());
        existingdUtilisateur.setSexe(administrateurDto.getSexe());
        Utilisateur updatedUtilisateur = userRepository.save(existingdUtilisateur);
        updatedUtilisateur.setId(id);
        log.info("utilisateur {} ",updatedUtilisateur);
        return modelMapper.map(updatedUtilisateur, UtilisateurDto.class);
    }
    public void deleteUtilisateur(Long id){
        //TODO add exption
        Utilisateur utilisateur = userRepository.findByIdAndDeletedFalse(id).orElseThrow(new ApiException(String.format("No User found for this id : %s",id),HttpStatus.BAD_REQUEST));
        utilisateur.setDeleted(true);
        userRepository.save(utilisateur);
    }
}
