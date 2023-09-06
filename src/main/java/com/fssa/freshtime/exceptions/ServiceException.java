package com.fssa.freshtime.exceptions;

public class ServiceException extends Exception {


	private static final long serialVersionUID = 1L;

	public ServiceException(String msg) {
        super(msg);
    }

	public ServiceException(String string, Exception e) {
		super(string,e);
	}

}
