package sait.tags;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.TagSupport;

public class Debug extends TagSupport 
{    
    @Override
    public int doStartTag() throws JspException 
    {
        ServletRequest req = pageContext.getRequest();
        String server = req.getServerName();
        String url = req.getParameter("debug");
        
        if(url == null || !((server.equals("localhost") || server.startsWith("test"))))
        {
            return SKIP_BODY;
            
        }              
        return EVAL_BODY_INCLUDE;
    }  
}
