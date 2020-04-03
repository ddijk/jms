package com.airhacks;

import org.junit.Test;

import javax.naming.*;
import java.io.PrintWriter;
import java.util.Hashtable;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void test() throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
        env.put(Context.PROVIDER_URL, "com.sun.enterprise.naming.SerialInitContextFactory");

        Context ctx = new InitialContext(env);

        final NamingEnumeration<Binding> bindingNamingEnumeration = ctx.listBindings("");

        while ( bindingNamingEnumeration.hasMore()) {

            System.out.println(bindingNamingEnumeration.next());
        }
    }

    public void printJndiContextAsHtmlList(PrintWriter writer, Context ctx, String name )
    {
        writer.println( "<ul>" );
        try {
            NamingEnumeration<Binding> en = ctx.listBindings( "" );
            while( en != null && en.hasMoreElements() ) {
                Binding binding = en.next();
                String name2 = name + (( name.length() > 0 ) ? "/" : "") + binding.getName();
                writer.println( "<li><u>" + name2 + "</u>: " + binding.getClassName() + "</li>" );
                if( binding.getObject() instanceof Context ) {
                    printJndiContextAsHtmlList( writer, (Context) binding.getObject(), name2 );
                }
            }
        } catch( NamingException ex ) {
            // Normalerweise zu ignorieren
        }
        writer.println( "</ul>" );
    }
}