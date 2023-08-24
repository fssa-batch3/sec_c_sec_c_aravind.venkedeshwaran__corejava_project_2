package com.fssa.freshtime.exceptions;

public class ServiceException extends Exception {

    public ServiceException(String msg) {
        super(msg);
    }

	public ServiceException(String string, Exception e) {
		super(string,e);
	}

}
