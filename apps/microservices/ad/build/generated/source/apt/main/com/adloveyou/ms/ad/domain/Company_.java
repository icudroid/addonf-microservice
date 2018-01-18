package com.adloveyou.ms.ad.domain;

import com.adloveyou.ms.ad.domain.enumeration.LegalStatus;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Company.class)
public abstract class Company_ {

	public static volatile SingularAttribute<Company, String> siren;
	public static volatile SingularAttribute<Company, String> name;
	public static volatile SingularAttribute<Company, LegalStatus> legalStatus;
	public static volatile SingularAttribute<Company, byte[]> logo;
	public static volatile SingularAttribute<Company, String> logoContentType;
	public static volatile SingularAttribute<Company, Long> id;
	public static volatile SingularAttribute<Company, String> siret;
	public static volatile SetAttribute<Company, Contact> contacts;
	public static volatile SetAttribute<Company, FileAttachement> attachements;

}

