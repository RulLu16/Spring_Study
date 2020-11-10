package chap3.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import chap3.config.AppCtx;
import chap3.spring.ChangePasswordService;
import chap3.spring.DuplicateMemberException;
import chap3.spring.MemberNotFoundException;
import chap3.spring.MemberRegisterService;
import chap3.spring.RegisterRequest;
import chap3.spring.WrongIdPasswordException;

public class MainForSpring {
	
	private static ApplicationContext ctx = null;

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		ctx = new AnnotationConfigApplicationContext(AppCtx.class); // spring 컨테이너 생성
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.println("Input command: ");
			String command = reader.readLine();
			if(command.equalsIgnoreCase("exit")) {
				System.out.println("terminate program.");
				break;
			}
			
			if(command.startsWith("new ")) {
				processNewCommand(command.split(" "));
				continue;
			} else if(command.startsWith("change ")) {
				processChangeCommand(command.split(" "));
				continue;
			}
			
			printHelp();
		}
	}
	
	private static void processNewCommand(String[] arg) {
		if(arg.length != 5) {
			printHelp();
			return;
		}
		
		// 생성된 bean 가져오기
		MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class);
		RegisterRequest req = new RegisterRequest();
		req.setEmail(arg[1]);
		req.setName(arg[2]);
		req.setPassword(arg[3]);
		req.setConfirmPassword(arg[4]);
		
		if(!req.isPasswordEqualToConfirmPassword()) {
			System.out.println("Wrong password!");
			return;
		}
		try {
			regSvc.regist(req);
			System.out.println("Assign complete");
		} catch(DuplicateMemberException e) {
			System.out.println("Already exist");
		}
	}
	
	private static void processChangeCommand(String[] arg) {
		if(arg.length != 4) {
			printHelp();
			return;
		}
		
		ChangePasswordService changePwdSvc = ctx.getBean("changePwdSvc", ChangePasswordService.class);
		
		try {
			changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("password changed");
		} catch(MemberNotFoundException e) {
			System.out.println("not exist");
		} catch(WrongIdPasswordException e) {
			System.out.println("wrong password");
		}
	}
	
	private static void printHelp() {
		System.out.println();
		System.out.println("Wrong command");
		System.out.println("Please use this command:");
		System.out.println("new / change ");
	}

}
