package com.projeto.Financas;

import com.projeto.Financas.securityJwt.JwtService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancasApplication {
	private JwtService jwtService;

	public FinancasApplication(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	// O job deve sempre ficar em uma classe separada. Cada job tem que ter uma entidade unica
	public static void main(String[] args) throws SchedulerException {
		SpringApplication.run(FinancasApplication.class, args);
		JobDetail job = JobBuilder.newJob(JwtService.class).build();
		Trigger t = TriggerBuilder.newTrigger().withIdentity("Crone").withSchedule(
				SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(1)
						.repeatForever()).build();
		Scheduler s = StdSchedulerFactory.getDefaultScheduler();
		s.start();
		s.scheduleJob(job,t);
	}


}
