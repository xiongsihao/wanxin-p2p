package cn.itcast.wanxinp2p.uaa.config;


import cn.itcast.wanxinp2p.uaa.domain.OauthClientDetails;
import cn.itcast.wanxinp2p.uaa.service.OauthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;


public class OauthUserApprovalHandler extends TokenStoreUserApprovalHandler {

    private OauthService oauthService;

    public OauthUserApprovalHandler() {
    }


    public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
        if (super.isApproved(authorizationRequest, userAuthentication)) {
            return true;
        }
        if (!userAuthentication.isAuthenticated()) {
            return false;
        }

        OauthClientDetails clientDetails = oauthService.loadOauthClientDetails(authorizationRequest.getClientId());
        return clientDetails != null && clientDetails.trusted();

    }

    public void setOauthService(OauthService oauthService) {
        this.oauthService = oauthService;
    }
}
