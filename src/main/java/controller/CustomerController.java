package controller;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.customer.CustomerServiceORM;
import service.customer.ICustomerService;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView("/home");
        List<Customer> customers = customerService.findAll();
        mav.addObject("customers", customers);
        return mav;
    }


    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView mav = new ModelAndView("/create");
        mav.addObject("c", new Customer());
        return mav;
    }
    @PostMapping("/create")
    public  String create(Customer customer){
        customerService.save(customer);

        return "redirect:/customers";
    }

    @GetMapping("/edit")
    public ModelAndView showEdit(int id){
        ModelAndView mav = new ModelAndView("/edit");
        Customer customer = customerService.findById(id);
        mav.addObject("c", customer);
        return mav;
    }

    @PostMapping("/edit")
    public String edit(Customer customer, int id){
        customerService.update(id, customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id1}/delete")
    public  String delete(@PathVariable int id1){
        customerService.delete(id1);
        return "redirect:/customers";
    }

}
