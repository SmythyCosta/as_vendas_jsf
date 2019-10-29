
package as.util;
import as.enums.TipoMensagem;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagem {

    public static void mensagem(String pMensagem, TipoMensagem pTipoMensagem) {
        if (pTipoMensagem == TipoMensagem.OK) {
            addMessagemOk(pMensagem);
        } else if(pTipoMensagem == TipoMensagem.AVISO){
            addMessagemAviso(pMensagem);
        }else{
            addMessagemErro(pMensagem);
        }
    }

    public static void addMessagemOk(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void addMessagemAviso(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void addMessagemErro(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
