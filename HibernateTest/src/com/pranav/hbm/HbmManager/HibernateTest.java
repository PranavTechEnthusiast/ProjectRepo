package com.pranav.hbm.HbmManager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.pranav.hbm.dao.Address;
import com.pranav.hbm.dao.Employee;
import com.pranav.hbm.dao.FourWheeler;
import com.pranav.hbm.dao.TwoWheeler;
import com.pranav.hbm.dao.UserDetails;
import com.pranav.hbm.dao.Vehicle;
import com.pranav.hbm.helper.Cache;

public class HibernateTest {

	//Cache reference for real time cache
	public static Map<String,Map> cache;
	//Actual cache Map that hold employee objects
	public static Map<Integer,Employee> cacheMap;
	//Employee Cache name
	public static final String employeeCacheName="EMPCACHE";
	public static final int president=7839;
	public static final int clerk=7369;
	
	
	public static void getCacheMethod(Session session, int empno){
		try{
			if(cacheMap==null){
				cacheMap=new HashMap<Integer,Employee>();
				Query query= session.createQuery("select e from Employee e where e.employeeNumber=:emp");
				query.setParameter("emp", empno);
				List<Employee> result=query.getResultList();
				for(Employee emp:result){
					cacheMap.put(emp.getEmployeeNumber(),emp);
				}
				//cache.put(employeeCacheName,cacheMap);
				System.out.println(empno+":DB Hit and emp cache updated with:"+cacheMap.get(empno).getEmployeeName());
				//System.out.println(cacheMap.get(empno).getEmployeeName());
			}
			else{
				cacheMap=cache.get(employeeCacheName);
				Employee e=(Employee) cacheMap.get(empno);
				System.out.println(empno+":Cache Hit before DB and found: "+e);
				if(e==null){
					Query query= session.createQuery("select e from Employee e where e.employeeNumber=:emp");
					query.setParameter("emp", empno);
					List<Employee> result=query.getResultList();
					if(result!=null && result.isEmpty()==Boolean.FALSE){
						for(Employee emp:result){
							cacheMap.put(emp.getEmployeeNumber(),emp);
						}
						//cache.put(employeeCacheName,cacheMap);
						System.out.println(empno+":DB Hit and emp cache updated with:" +cacheMap.get(empno).getEmployeeName());
					}
					else{
						System.out.println(empno+":Regret:Employee not present in the table.");
						System.out.println(1^2);
						System.out.println(1>>2);
						System.out.println(1<<2);
						System.out.println(1>>>2);
					}
				}
			}
		}catch(Exception e){}
		finally{
			cache.put(employeeCacheName,cacheMap);
			cacheMap=cache.get(employeeCacheName);
			//System.out.println(cacheMap.get(empno).getEmployeeName());
		}
	}
	
	public static void main(String[] args) {
		Vehicle vehicle=new Vehicle();
		Vehicle vehicle1=new Vehicle();
		UserDetails user1=new UserDetails();
		
		vehicle.setVehicleName("Car:Maruti 800");
		vehicle.setUser(user1);
		
		vehicle1.setVehicleName("Car:Jeep");
		vehicle1.setUser(user1);
		
		TwoWheeler bike = new TwoWheeler();
		bike.setSteeringHandle("steeringHandle");
		bike.setVehicleName("Ducati");
		FourWheeler car = new FourWheeler();
		car.setSteeringWheel("steeringWheel");
		car.setVehicleName("AUDI");
		user1.getVehicles().add(vehicle);
		user1.getVehicles().add(vehicle1);
		
		
		//user1.setUserId(2);
		user1.setUserName("Jo");
		Address resiaddr = new Address();
		resiaddr.setStreet("123 SB Road");
		resiaddr.setCity("Mumbai");
		resiaddr.setState("Maharashtra");
		resiaddr.setPincode(413001);
		user1.setResidenceAddr(resiaddr);
		
		Address addr = new Address();
		addr.setStreet("123 Avenue Street");
		addr.setCity("Mumbai");
		addr.setState("Maharashtra");
		addr.setPincode(413001);
		user1.setOfficeAddr(addr);
		
		user1.getAssets().add("Equity Assets");
		user1.getAssets().add("Rates Assets");
		user1.getAssets().add("Commodity Assets");
		user1.getAssets().add("Forex Assets");
		//Configuration cfg=new Configuration();
		
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		
		cache=Cache.getCache();
		
		
		Session session=sessionFactory.openSession();
		
		try{
			session.beginTransaction();
			/*
			session.save(vehicle);
			session.save(vehicle1);
			session.save(car);
			session.save(bike);
			session.save(user1);*/

			session.getTransaction().commit();
			session.beginTransaction();
			//cacheMap=cache.get(employeeCacheName);
			HibernateTest.getCacheMethod(session, president);
			//cacheMap=cache.get(employeeCacheName);
			HibernateTest.getCacheMethod(session, clerk);
			
			//cacheMap=cache.get(employeeCacheName);
			HibernateTest.getCacheMethod(session, clerk);
			HibernateTest.getCacheMethod(session, 123);
			session.getTransaction().commit();
			
		}
		catch(Exception e){
			e.printStackTrace();
			session.getTransaction().commit();	
		}
		session.close();
		
	}

}
