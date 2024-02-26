package com.contract.common.provider;

import java.util.Hashtable;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LDAP {
	public static String authMethod = "simple";
	public static String ldapVersion = "3";
	// public static String ldapHost = "10.3.12.17";
	public static String ldapHost = "mobifone.vn";
	public static String ldapPort = "389";


	public static boolean authentication(String ldapDn, String ldapPw) {
		DirContext ctx = null;
		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://" + ldapHost + ":" + ldapPort);
		env.put(Context.SECURITY_AUTHENTICATION, authMethod);
		env.put(Context.SECURITY_PRINCIPAL, ldapDn);
		env.put(Context.SECURITY_CREDENTIALS, ldapPw);
		env.put("java.naming.ldap.version", ldapVersion);
		try {
			ctx = new InitialDirContext(env);

			return true;

		} catch (AuthenticationException authEx) {
			System.out.println(authEx.getMessage());
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (ctx != null)
					ctx.close();
			} catch (NamingException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}

	// public static boolean isValid(String email) {
	// String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@"
	// + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

	// Pattern pat = Pattern.compile(emailRegex);
	// if (email == null)
	// return false;
	// return pat.matcher(email).matches();
	// }
}
