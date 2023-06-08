package web.projectTeam.busInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class BusInfoController {
	@Autowired
	private SiteUserRepository userRepository;

	@GetMapping("/bus")
	public String busInfo(Model model) {
		return "busInfo";
	}

	@GetMapping("/bus1")
	public String busInfo1(Model model) {
		return "busInfo1";
	}
	
	@GetMapping("/bus2")
	public String busInfo2(Model model) {
		return "busInfo2";
	}

	@GetMapping("/bus3")
	public String busInfo3(Model model) {
		return "busInfo3";
	}

	//signup
	@GetMapping(path="/signup")
	public String signup(Model model) {
		model.addAttribute("siteuser", new SiteUser());
		return "signup";
	}
	
	@PostMapping(path="/signup")
	public String signup(@ModelAttribute SiteUser user, Model model) {
		userRepository.save(user);
		model.addAttribute("name", user.getName());
		return "signup_done";
	}

	@PostMapping(path="/find")
	public String findUser(@RequestParam(name="email") String email, HttpSession session, Model model, RedirectAttributes rd) {
		SiteUser user = userRepository.findByEmail(email);
		if(user != null) {
			model.addAttribute("user", user);
			return "find_done";
		}
		rd.addFlashAttribute("reason", "wrong email");
		return "redirect:/error";
	}

	//login
	@GetMapping(path="/login")
	public String loginForm() {
		return "login";
	}
	@PostMapping(path="/login")
	public String loginUser(@RequestParam(name="username") String email, @RequestParam(name="password") String passwd, HttpSession session, RedirectAttributes rd) {
		SiteUser user = userRepository.findByEmail(email);
		if(user != null) {
			if(passwd.equals(user.getPasswd())) {
				session.setAttribute("email", email);
				return "login_done";
			}
		}
		rd.addFlashAttribute("reason", "wrong password");
		return "redirect:/error";
	}

	//userMod
	@GetMapping(path="/userMod")
	public String userMod(Model model) {
		return "user_mod";
	}
	
	
	@GetMapping(path="/logout")
	public String logout(HttpSession session) {
		session.invalidate( );
		return "bus";
	}
	
	@Autowired
	private ArticleRepository articleRepository;
	@Value("${spring.servlet.multipart.location}")
	private String base; // 파일 저장 폴더 
	private String tmpFileName = "";
	
	@GetMapping(path="/community/write")
	public String bbsForm(Model model, HttpSession session, RedirectAttributes rd) {
		String email = (String) session.getAttribute("email");
		if (email==null) {
			rd.addFlashAttribute("reason", "login required");
			return "redirect:/error";
		}
		
		model.addAttribute("article", new Article());
		return "new_article";
	}
	
	@PostMapping(path="/community/add")
	public String addArticle(@ModelAttribute Article article, Model model, @RequestParam MultipartFile file) throws IllegalStateException, IOException{
		if( ! file.isEmpty( )) {
			String newName = file.getOriginalFilename( );
			newName = newName.replace(' ', '_');
			FileDto dto = new FileDto(newName, file.getContentType( ));
			String[] filenameAry = dto.getFileName().split("\\.");
			
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd_HHmmss");
	        String timeStamp = date.format(new Date());
	        tmpFileName=filenameAry[0]+"_"+timeStamp+"."+filenameAry[1];
			File upfile = new File(filenameAry[0]+"_"+timeStamp+"."+filenameAry[1]);
			file.transferTo(upfile);
			model.addAttribute("file", dto);
			article.setFilePath(tmpFileName);
		}
		articleRepository.save(article);
		model.addAttribute("article", article);
		return "saved";
	}
	
	@GetMapping(path="/community")
	public String getAllArticles(Model model, HttpSession session, RedirectAttributes rd) {
		String email = (String) session.getAttribute("email");
		if (email==null) {
			rd.addFlashAttribute("reason", "login required");
			return "redirect:/error";
		}
		Iterable<Article> data = articleRepository.findAll();
		model.addAttribute("articles", data);
		return "community";
	}
	
	@GetMapping(path="/read")
	public String readArticle(@RequestParam(name="num") String num, HttpSession session, Model model) {
		Long no = Long.valueOf(num);
		Article article = articleRepository.getReferenceById(no);
		
		model.addAttribute("article", article);
		model.addAttribute("filename", article.getFilePath());
		return "article";
	}
	
	// 파일 업로드
	@PostMapping(path="/upload")
	public String upload(@RequestParam MultipartFile file, Model model) throws IllegalStateException, IOException {
		if( ! file.isEmpty( )) {
			String newName = file.getOriginalFilename( );
			newName = newName.replace(' ', '_');
			FileDto dto = new FileDto(newName, file.getContentType( ));
			String[] filenameAry = dto.getFileName().split("\\.");
			
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd_HHmmss");
	        String timeStamp = date.format(new Date());
	        tmpFileName=filenameAry[0]+"_"+timeStamp+"."+filenameAry[1];
			File upfile = new File(filenameAry[0]+"_"+timeStamp+"."+filenameAry[1]);
			file.transferTo(upfile);
			model.addAttribute("file", dto);
		}
		return "result";
 	}
	
	@GetMapping(path="/upload")
	public String visitUpload( ) {
		return "uploadForm";
	}
	
	// 파일 다운로드
	@GetMapping(path = "/download")
	public ResponseEntity<Resource> download(@ModelAttribute FileDto dto) throws IOException {
		// Path path = Paths.get(base + "/" + dto.getFileName());
		Path path = Paths.get(base + "/" + tmpFileName);
		String contentType = Files.probeContentType(path);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("attachment")
				.filename(dto.getFileName(), StandardCharsets.UTF_8).build());
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		Resource rsc = new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<>(rsc, headers, HttpStatus.OK);
	}
}
