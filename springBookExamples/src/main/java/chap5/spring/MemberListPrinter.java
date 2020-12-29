package chap5.spring;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("listPrinter")
public class MemberListPrinter {
	private MemberDao memberDao;
	private MemberPrint printer;
	
	public MemberListPrinter() {
	}
	
	@Autowired
	public void setMemberDao(MemberDao memberDao) { 
		this.memberDao = memberDao;
	}
	
	@Autowired 
	@Qualifier("printer") 
	public void setMemberPrinter(MemberPrint printer) {
		this.printer = printer;
	}
	
	public void printAll() {
		Collection<Member> members = memberDao.selectAll();
		members.forEach(m->printer.print(m));
	}
}
