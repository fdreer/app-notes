package com.franco.appnotes;

import com.franco.appnotes.auth.AuthRequest;
import com.franco.appnotes.auth.AuthResponse;
import com.franco.appnotes.auth.IAuthService;
import com.franco.appnotes.notes.INoteService;
import com.franco.appnotes.notes.NoteRepository;
import com.franco.appnotes.notes.dto.NoteCreateDto;
import com.franco.appnotes.security.RegisterRequest;
import com.franco.appnotes.users.IUserService;
import com.franco.appnotes.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AppNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppNotesApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			IUserService userService,
			INoteService noteService,
			UserRepository userRepository,
			NoteRepository noteRepository,
			IAuthService authService
	)
	{
		return args -> {
//			initDB(userRepository, noteService, authService);
//			noteRepository.deleteAll();
//			userRepository.deleteAll();
		};
	}

	private static void initDB(UserRepository userRepository,
							   INoteService noteService,
							   IAuthService authService)
	{
//		User me = User.builder()
//				.username("franco00")
//				.password("12345")
//				.role(Role.USER)
//				.build();
//
//		User user1 = userRepository.save(me);

		RegisterRequest franco00 = RegisterRequest.builder()
				.username("franco")
				.password("8B72g3k5@")
				.build();

		AuthResponse register = authService.register(franco00);

		NoteCreateDto note1 = NoteCreateDto.builder()
				.title("Primera nota")
				.content("Contenido de la primera nota")
				.important(true)
				.userId(register.id())
				.build();

		noteService.createNote(note1);

		NoteCreateDto note2 = NoteCreateDto.builder()
				.title("Segunda nota")
				.content("Contenido de la segunda nota")
				.important(true)
				.userId(register.id())
				.build();

		AuthRequest auth = AuthRequest.builder()
				.username("franco00")
				.password("12345")
				.build();

		AuthResponse login = authService.login(auth);
		System.out.println(login);
	}
}

