package com.parvanpajooh.issuemanager.mvc;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

public class HelloDialect extends AbstractDialect {

	  public HelloDialect() {
	    super();
	  }

	  //
	  // All of this dialect's attributes and/or tags
	  // will start with 'hello:'
	  //
	  public String getPrefix() {
	    return "text";
	  }


	  //
	  // The processors.
	  //
	  @Override
	  public Set<IProcessor> getProcessors() {
	    final Set<IProcessor> processors = new HashSet<IProcessor>();
	    processors.add(new LineSeparatorAttrProcessor());
	    return processors;
	  }

	}
