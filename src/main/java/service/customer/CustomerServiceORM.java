package service.customer;

import model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;



public class CustomerServiceORM implements ICustomerService{

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;


    public List<Customer> findAll() {
        String query = "SELECT c FROM Customer AS c";
        TypedQuery<Customer> customerTypedQuery = entityManager.createQuery(query, Customer.class);
        return customerTypedQuery.getResultList();
    }

    @Override
    public void save(Customer customer) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
//            Customer customer1 = customer;
//            customer1.setName(customer.getName());
//            customer1.setAddress(customer.getAddress());
            session.saveOrUpdate(customer);
            transaction.commit();

//            return customer1;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        finally {
            if(session !=null){
                session.close();
            }
        }
//        return null;
    }

    @Override
    public Customer findById(int id) {
        String queryStr = "SELECT c FROM Customer AS c WHERE c.id =:ha";
        Customer customer = entityManager.createQuery(queryStr,Customer.class)
                .setParameter("ha",id).getSingleResult();
        return customer;
    }

    @Override
    public void update(int id, Customer customer) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();


//            Customer customer1 = findById(id);
//
//            customer1.setName(customer.getName());
//            customer1.setAddress(customer.getAddress());
//            customer1.setEmail(customer.getEmail());
//            customer1.setImg(customer.getImg());


            session.update(customer);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        finally {
            if(session !=null){
                session.close();
            }
        }
    }

    @Override
    public void remove(int id) {
        Customer customer = findById(id);
        if (customer != null) {
            entityManager.remove(customer);
//            Session session = sessionFactory.openSession();
//           session.remove(customer);
        }
    }

    @Override
    public void delete(int id) {
        String queryStr = "DELETE  FROM Customer AS c WHERE c.id =:ha";
        Session session = sessionFactory.openSession();
        session.createQuery(queryStr, Customer.class).setParameter("ha", id);
    }


}
