package com.franco.appnotes;

import com.franco.appnotes.dto.NoteDtoRequest;
import com.franco.appnotes.repository.NoteRepository;
import com.franco.appnotes.repository.UserRepository;
import com.franco.appnotes.security.AuthenticationRequest;
import com.franco.appnotes.security.AuthenticationResponse;
import com.franco.appnotes.security.AuthenticationService;
import com.franco.appnotes.security.RegisterRequest;
import com.franco.appnotes.services.NoteService;
import com.franco.appnotes.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppNotesApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			UserService userService,
			NoteService noteService,
			UserRepository userRepository,
			NoteRepository noteRepository,
			AuthenticationService authService
	)
	{
		return args -> {
			initDB(userRepository, noteService, authService);
//			noteRepository.deleteAll();
//			userRepository.deleteAll();
		};
	}

	private static void initDB(UserRepository userRepository,
							   NoteService noteService,
							   AuthenticationService authService)
	{
//		User me = User.builder()
//				.username("franco00")
//				.password("12345")
//				.role(Role.USER)
//				.build();
//
//		User user1 = userRepository.save(me);

		RegisterRequest franco00 = RegisterRequest.builder()
				.username("franco00")
				.password("12345")
				.build();

		AuthenticationResponse register = authService.register(franco00);

		NoteDtoRequest note1 = NoteDtoRequest.builder()
				.title("Primera nota")
				.content("Contenido de la primera nota")
				.completed(false)
				.important(true)
				.userId(register.id())
				.build();

		noteService.createNote(note1);

		NoteDtoRequest note2 = NoteDtoRequest.builder()
				.title("Segunda nota")
				.content("Contenido de la segunda nota")
				.completed(false)
				.important(true)
				.userId(register.id())
				.build();

		AuthenticationRequest auth = AuthenticationRequest.builder()
				.username("franco00")
				.password("12345")
				.build();

		AuthenticationResponse login = authService.login(auth);
		System.out.println(login);
	}
}

