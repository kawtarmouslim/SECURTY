package com.aitnacer.LabXpert.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.aitnacer.LabXpert.dtos.UtilisateurDto;
import com.aitnacer.LabXpert.entity.EnumSexe;
import com.aitnacer.LabXpert.entity.UserRole;
import com.aitnacer.LabXpert.entity.Utilisateur;
import com.aitnacer.LabXpert.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    private ModelMapper modelMapper;
    @BeforeEach
    public void setup(){
        userRepository = Mockito.mock(UserRepository.class);
        modelMapper = new ModelMapper();
        userService = new UserServiceImpl(userRepository,modelMapper);
    }
    @Test
    void gevienListOfUTilisateursDTo() {
        Utilisateur user1 = Utilisateur.builder()
                .id(1l)
                .nom("John")
                .prenom("Doe")
                .Adresse("123 Main St")
                .telephone("123456789")
                .sexe(EnumSexe.MALE)
                .deleted(false)
                .userName("john.doe")
                .password("password123")
                .role(UserRole.TECHNICIEN)
                .build();

        Utilisateur user2 = Utilisateur.builder()
                .id(2l)
                .nom("Jane")
                .prenom("Doe")
                .Adresse("456 Oak St")
                .telephone("987654321")
                .sexe(EnumSexe.FEMALE)
                .deleted(false)
                .userName("jane.doe")
                .password("securepass")
                .role(UserRole.RESPONSABLE)
                .build();
        given(userRepository.findByDeletedFalse()).willReturn(Stream.of(user1,user2).collect(Collectors.toList()));
        List<UtilisateurDto> utilisateurDtos = userService.getAllUtilisateur();
        assertThat(utilisateurDtos).isNotNull();
        assertThat(utilisateurDtos.size()).isEqualTo(2);
        System.out.println(utilisateurDtos);

    }

    @Test
    void getUtilisateurById() {
        // Arrange
        Long id = 1L;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(id);
        utilisateur.setNom("John");
        utilisateur.setPrenom("Doe");
        utilisateur.setAdresse("123 Main St");
        utilisateur.setTelephone("1234567890");
        utilisateur.setSexe(EnumSexe.MALE);
        utilisateur.setDeleted(false);
        utilisateur.setUserName("johndoe");
        utilisateur.setPassword("password");
        utilisateur.setRole(UserRole.RESPONSABLE);

        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setId(id);
        utilisateurDto.setNom("John");
        utilisateurDto.setPrenom("Doe");
        utilisateurDto.setAdresse("123 Main St");
        utilisateurDto.setTelephone("1234567890");
        utilisateurDto.setSexe(EnumSexe.MALE);
        utilisateurDto.setDeleted(false);
        utilisateurDto.setUserName("johndoe");
        utilisateurDto.setPassword("password");
        utilisateurDto.setRole(UserRole.RESPONSABLE);

        when(userRepository.findByIdAndDeletedFalse(id)).thenReturn(Optional.of(utilisateur));

        // Act
        UtilisateurDto result = userService.getUtilisateurById(id);

        // Assert
        assertEquals(utilisateurDto, result);

    }

    @Test
    void createUtilisateur() {
        UtilisateurDto userDto = UtilisateurDto.builder()
                .nom("John")
                .prenom("Doe")
                .Adresse("123 Main St")
                .telephone("123456789")
                .sexe(EnumSexe.MALE)
                .deleted(false)
                .userName("john.doe")
                .password("password123")
                .role(UserRole.TECHNICIEN)
                .build();
        Utilisateur user = Utilisateur.builder()
                .id(1l)
                .nom("John")
                .prenom("Doe")
                .Adresse("123 Main St")
                .telephone("123456789")
                .sexe(EnumSexe.MALE)
                .deleted(false)
                .userName("john.doe")
                .password("password123")
                .role(UserRole.TECHNICIEN)
                .build();

        given(userRepository.save(Mockito.any(Utilisateur.class))).willReturn(user);


        // Call the method to test
        UtilisateurDto resultDto = userService.createUtilisateur(userDto);
        assertThat(resultDto).isNotNull();
        System.out.println(resultDto);

    }

    @Test
    void updateUtilisateur() {
        UtilisateurDto userDto = UtilisateurDto.builder()
                .nom("John")
                .prenom("Updated")
                .Adresse("123 Main St")
                .telephone("123456789")
                .sexe(EnumSexe.MALE)
                .deleted(false)
                .userName("john.doe")
                .password("password123")
                .role(UserRole.TECHNICIEN)
                .build();
        Utilisateur user = Utilisateur.builder()
                .id(1l)
                .nom("Updated")
                .prenom("Doe")
                .Adresse("123 Main St")
                .telephone("123456789")
                .sexe(EnumSexe.MALE)
                .deleted(false)
                .userName("john.doe")
                .password("password123")
                .role(UserRole.TECHNICIEN)
                .build();

        given(userRepository.save(Mockito.any(Utilisateur.class))).willReturn(user);


        // Call the method to test
        UtilisateurDto resultDto = userService.createUtilisateur(userDto);
        assertThat(resultDto).isNotNull();
        System.out.println(resultDto);

    }

    @Test
    void deleteUtilisateur() {
        Utilisateur user1 = Utilisateur.builder()
                .id(1l)
                .nom("John")
                .prenom("Doe")
                .Adresse("123 Main St")
                .telephone("123456789")
                .sexe(EnumSexe.MALE)
                .deleted(false)
                .userName("john.doe")
                .password("password123")
                .role(UserRole.TECHNICIEN)
                .build();
        given(userRepository.findByIdAndDeletedFalse(1l)).willReturn(Optional.of(user1));
        given(userRepository.save(user1)).willReturn(user1);
        user1.setDeleted(true);
        userService.deleteUtilisateur(1l);

        assertThat(user1.isDeleted()).isTrue();

    }
}