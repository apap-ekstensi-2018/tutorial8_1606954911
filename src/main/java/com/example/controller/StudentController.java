package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.StudentModel;
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;


   @RequestMapping("/index")
    public String index (Model model)
    {
        model.addAttribute("title","home");
    		return "index";
    }
   
   /*@RequestMapping("/login")
	public String login () {
	 return "login";
	}*/


    @RequestMapping("/student/add")
    public String add (Model model)
    {
    		model.addAttribute("title","Menambah Mahasiswa");
    		return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (Model model,
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
        StudentModel student = new StudentModel (npm, name, gpa);
        studentDAO.addStudent (student);
        model.addAttribute("title","Sukses Menambah Mahasiswa");
        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
    		return "redirect:/student/view/" + npm;
        /*StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            model.addAttribute("title","Lihat Mahasiswa");
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            model.addAttribute("title","Mahasiswa Tidak Ditemukan");
            return "not-found";
        }*/
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            model.addAttribute("title","Lihat Mahasiswa");
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            model.addAttribute("title","Mahasiswa Tidak Ditemukan");
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);
        model.addAttribute("title","Daftar Mahasiswa");
        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {	
    		StudentModel student = studentDAO.selectStudent (npm);
    			if (student != null) {
	            studentDAO.deleteStudent(npm);
	            model.addAttribute("title","Mahasiswa Telah Dihapus");
	            return "delete";
    			}else {
    				model.addAttribute("title","Mahasiswa Tidak Ditemukan");
    				return "not-found";
    			}
		
      
        		
    } 
    
    @RequestMapping("/student/update/{npm}")
    public String update (Model model, @PathVariable(value = "npm") String npm)
    {	
    		StudentModel student = studentDAO.selectStudent (npm);
    			if (student != null) {
	            model.addAttribute("student",student);
	            model.addAttribute("title","Update Mahasiswa");
	            return "form-update";
    			}else {
    				model.addAttribute("npm",npm);
    				model.addAttribute("title","Mahasiswa Tidak Ditemukan");
    				return "not-found";
    			}
        		
    } 
    
   /*@RequestMapping(value="/student/update/submit", method = RequestMethod.POST)
    public String updateSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
        StudentModel student = new StudentModel (npm, name, gpa);
        studentDAO.updateStudent (student);
s
        return "success-update";
    }*/
    
    @PostMapping(value="/student/update/submit")
    public String updateSubmit (Model model,@ModelAttribute StudentModel student)
    //public String updateSubmit (@Valid StudentModel student, BindingResult br)
    {
    		//if (br.hasErrors()) {
    		//	return "error-update";
    		//}else {
    			 studentDAO.updateStudent (student);
    			 model.addAttribute("title","Submit Mahasiswa");
    		        return "success-update";
    		//}
       
    }
    

}
