package com.adloveyou.ms.ad.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contact.class)
public abstract class Contact_ {

	public static volatile SingularAttribute<Contact, String> firstname;
	public static volatile SetAttribute<Contact, MeanOfContact> meanOfContacts;
	public static volatile SingularAttribute<Contact, Company> company;
	public static volatile SingularAttribute<Contact, Long> id;
	public static volatile SingularAttribute<Contact, String> lastname;

}

