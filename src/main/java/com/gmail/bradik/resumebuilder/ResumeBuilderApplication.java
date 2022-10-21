package com.gmail.bradik.resumebuilder;

import lombok.RequiredArgsConstructor;
import com.gmail.bradik.resumebuilder.util.Mode;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@SpringBootApplication
public class ResumeBuilderApplication implements ApplicationRunner  {

	private final ResumeBuilder resumeBuilder;

	public static void main(String[] args) {
		SpringApplication.run(ResumeBuilderApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		String data = Optional.ofNullable(args.getOptionValues("data"))
				.map(Collection::stream)
				.flatMap(Stream::findFirst)
				.orElseThrow(() -> new IllegalArgumentException("--data not found"));

		String photo = Optional.ofNullable(args.getOptionValues("photo"))
				.map(Collection::stream)
				.flatMap(Stream::findFirst)
				.orElseThrow(() -> new IllegalArgumentException("--photo not found"));

		resumeBuilder.build(Path.of(data), Path.of(photo));

	}
}
