package com.beyond.basic.controller;

import com.beyond.basic.domain.Hello;
import com.beyond.basic.domain.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Controller
// 해당 클래스가 컨트롤러(사용자가 요청을 처리하고 응답하는 편의기능) 임을 명시
@RequestMapping("/hello") // 클래스 차원의 url 매핑시 RequestMapping 사용
//@RestController
// @ResponseBody 의 반복된 중복사용으로 코드의 가독성
public class HelloController {
    // case1 : 사용자가 서버에게 화면요청(get요청)
    // case2 : @ResponseBody가 붙을 경우 단순 String 데이터 리턴(get요청)
    @GetMapping("/")
    // getMapping 을 통해서 get 요청을 처리하고 url 패턴을 명시
    //@ResponseBody
    // responseBody 을 사용하면 화면이 아닌 데이터를 return
    // if responseBody 가 없으면 스프링은 resource/templates/helloworld.html 을 찾아서 리턴
    public String helloWorld() {
        return "helloworld";
        // return "hello world";
    }


    // case 3 : 사용자가 json 데이터 요청(get)
    // data 을 리턴하되, json 형식으로 return
    // method명은 helloJson, url 패턴은 /hello/json
    @GetMapping("/json")
    //@RequestMapping(value = "/json", method = RequestMethod.GET) // 메서드 차원에서도 RequestMapping 사용 가능
    @ResponseBody
    //responseBody을 사용하면서 객체를 리턴하면 자동으로 직렬화
    public Hello helloJson() throws JsonProcessingException {
        Hello hello = new Hello();
        hello.setName("lee");
        hello.setEmail("lee@naver.com");
        //ObjectMapper mapper = new ObjectMapper();
        //String value = mapper.writeValueAsString(hello);
        //return value;
        return hello;
    }


    // case4 : 사용자가 json 데이터를 요청하되, parameter 형식으로 특정객체 요청
    // get 요청 중 특정 데이터 요청
    // parameter 형식 : ?name=hongildong
    // http://localhost:8080/hello/param1?name=hongildong 주소 호출
    @GetMapping("/param1")
    @ResponseBody
    public Hello Param1(@RequestParam(value = "name") String inputValue) {

        Hello hello = new Hello();
        hello.setName(inputValue);
        hello.setEmail("hongildong@naver.com");
        System.out.println(hello);
        return hello;

    }

    // url 패턴은 : /param2
    // 메서드 명 Param2
    // parameter 2개 : name, email => hello 객체 생성 후 리턴
    // 요청방식 : ?name=xxx&email=xxx@naver.com
    // http://localhost:8080/hello/param2?name=hongildong&email=hong@naver.com 주소 호출
    @GetMapping("/param2")
    @ResponseBody
    public Hello Param2(@RequestParam(value = "name") String inputName, @RequestParam(value = "email") String inputEmail) {
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail(inputEmail);
        return hello;
    }

    // case5 : parameter 형식으로 요청하되, 서버에서 데이터 바인딩 하는 형식
    // parameter가 다수 존재 할 경우 객체로 대체 가능, 객체의 각 변수에 맞게 알아서 바인딩(데이터 바인딩)
    // 요청방식 : ?name=xxx&email=xxx@naver.com&password=xxxx
    // 데이터 바인딩 조건 : 기본생성자, setter
    // http://localhost:8080/hello/param3?name=hongildong&email=hong@naver.com&password=1234 주소 호출
    @GetMapping("/param3")
    @ResponseBody
    public Hello Param3(Hello hello) {
        return hello;
    }

    // case6 : 서버에서 화면에 데이터를 넣어 사용자에게 return( Model 객체 사용)
    // SSR
    // http://localhost:8080/hello/model-param?name=hongildong&email=hong@naver.com&password=1234 주소 호출
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "name") String inputName, Model model) {
        //  model 객체의 name 이라는 키값에 value을 세팅하면 해당 key:value는 화면으로 전달
        model.addAttribute("name", inputName);
        return "helloworld";
    }

    // CASE7 : pathVariable 방식을 통해서 사용자로부터 값을 받아 화면 리턴
    // localhost:8080/hello/model-path/hongildong  name
    // localhost:8080/author/1 또는 author?id=1     id
    // pathVariable 방식은 url을 통해서 자원의 구조를 명확하게 표현함으로, 좀 더 restful한 형식
    @GetMapping("/model-path/{inputName}")
    public String modelPath(@PathVariable String inputName, Model model) {
        model.addAttribute("name", inputName);
        return "helloworld";
    }

    /* post 요청(사용자 입장에서 서버에 데이터를 주는 상황) */
    //case1 : url 인코딩 방식(text만) 전송
    // 형식 : key1=value1&key2=value2&key3=value3
    @PostMapping("/form-post1") // getmapping 과 같은 url 패턴 사용도 가능
    @ResponseBody
    public String formPost1(@RequestParam(value = "name") String inputName, @RequestParam(value = "email") String inputEmail, @RequestParam(value = "password") String inputPassword){
        // 사용자로부터 받아온 내용 출력
        System.out.println(inputName);
        System.out.println(inputEmail);
        System.out.println(inputPassword);
        return "ok";
    }
    @PostMapping("/form-post2") // getmapping 과 같은 url 패턴 사용도 가능
    @ResponseBody
    public String formPost2(@ModelAttribute Hello hello){
        // modelAttribute 생략가능
        System.out.println(hello);
        return "ok";
    }

    @GetMapping("/form-view")
    public String formView() {
        return "form-view";
    }

    // case2 : multipart / form-data 방식 ( text 와 파일) 전송
    // url명 : form-file-post, 메서드명 : formFilePost, 화면명: form-view2
    // form 태그 : name, email ,password, file
    @GetMapping("/form-file-view")
    public String formFileView() {
        return "form-file-view";
    }

    @PostMapping("/form-file-post")
    @ResponseBody
    public String formFileHandle(@ModelAttribute Hello hello, @RequestParam(value = "file")MultipartFile file) {
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }

    // case3 : js을 활용한 form 데이터 전송(text)
    @GetMapping("/axios-form-view")
    public String axiosFormView() {
        return "axios-form-view";
    }

    @PostMapping("/axios-form-view")
    @ResponseBody
    // axios 을 통해서 넘어오는 형식이 key1=value1&key2=value2 등 url 인코딩 방식
    public String axiosFormPost(@ModelAttribute Hello hello) {
        System.out.println(hello);
        return "ok";
    }

    // case4 : js을 활용한 form데이터 전송(+file)
    @GetMapping("/axios-form-file-view")
    public String axiosFormFileView() {
        return "axios-form-file-view";
    }
    @PostMapping("/axios-form-file-view")
    @ResponseBody
    public String axiosFormFileViewPost(@ModelAttribute Hello hello, @RequestParam(value = "file")MultipartFile file) {
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }
    // case5 : js을 활용한 json데이터 전송
    // url 패턴 : axios-json-view, 화면명 : axios-json-view, get요청 메서드 : 동일,
    // post 요청 메서드 : axiosJsonpost
    @GetMapping("axios-json-view")
    public String axiosJsonView() {
        return "axios-json-view";
    }
    // json으로 전송한 데이터를 받을 때에는 @RequestBody 어노테이션 사용
    @PostMapping("axios-json-view")
    @ResponseBody
    public String axiosJsonPost(@RequestBody Hello hello) {
        System.out.println(hello);
        return "ok";
    }

    // case6 : js을 활용한 json데이터 전송(+file)
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFileView() {
        return "axios-json-file-view";
    }

    @PostMapping("/axios-json-file-view")
    @ResponseBody
    //RequestPart : 파일과 json을 처리할 때 주로 사용하는 어노테이션
    public String axiosJsonFilePost(@RequestPart("hello") Hello hello, @RequestPart("file")MultipartFile file) {
        // form 데이터를 통해서 JSON과 FILE을 처리할 떄 RequestPart 어노테이션을 많이 사용
//    public String axiosJsonFilePost(@RequestParam(value = "hello") String hello, @RequestParam(value = "file") MultipartFile file) throws JsonProcessingException {
        System.out.println(hello);
        //String으로 받은 뒤 수동으로 객체 변환
//        ObjectMapper mapper = new ObjectMapper();
//        Hello h1 = mapper.readValue(hello, Hello.class);
//        System.out.println(h1.getName());
        System.out.println(file.getOriginalFilename());
        return "ok";
    }

    // case7 : js을 활용한 json데이터 전송(+여러 file)
    @GetMapping("/axios-json-multi-file-view")
    public String axiosJsonMultiFileView() {
        return "axios-json-multi-file-view";
    }

    @PostMapping("/axios-json-multi-file-view")
    @ResponseBody
    public String axiosJsonMultiFilePost(@RequestPart("hello") Hello hello, @RequestPart("files") List<MultipartFile> files) {
        System.out.println(hello);
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }
        return "ok";
    }

    // case 8 : 중첩된 JSON 데이터 처리
    // {name: 'hongildong', email:'hong@naver.com', scores:[math:60, science:70, english:100]};
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView() {
        return "axios-nested-json-view";
    }
    @PostMapping("/axios-nested-json-view")
    @ResponseBody
    public String axiosNestedJsonPost(@RequestBody Student student) {
        System.out.println(student);
        return "ok";
    }

}
