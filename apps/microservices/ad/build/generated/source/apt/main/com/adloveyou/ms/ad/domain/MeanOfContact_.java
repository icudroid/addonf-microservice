package com.adloveyou.ms.ad.domain;

import com.adloveyou.ms.ad.domain.enumeration.MeanOfContactType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MeanOfContact.class)
public abstract class MeanOfContact_ {

	public static volatile SingularAttribute<MeanOfContact, Contact> contact;
	public static volatile SingularAttribute<MeanOfContact, Long> id;
	public static volatile SingularAttribute<MeanOfContact, MeanOfContactType> type;
	public static volatile SingularAttribute<MeanOfContact, String> value;

}

