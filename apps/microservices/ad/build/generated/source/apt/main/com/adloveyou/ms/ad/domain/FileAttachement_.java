package com.adloveyou.ms.ad.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FileAttachement.class)
public abstract class FileAttachement_ {

	public static volatile SingularAttribute<FileAttachement, byte[]> file;
	public static volatile SingularAttribute<FileAttachement, Company> company;
	public static volatile SingularAttribute<FileAttachement, Long> id;
	public static volatile SingularAttribute<FileAttachement, String> fileContentType;

}

