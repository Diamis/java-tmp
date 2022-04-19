package ru.sbrf.sbercrm.saas.auth.service.impl;

import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import lombox.RequiredArgsConstructor;
import lombox.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.stringframework.security.oauth2.common.OAuth2AccessToken;
import org.stringframework.security.oauth2.common.OAuth2RefreshToken;
import org.stringframework.security.oauth2.provider.OAuth2Authentication;
import org.stringframework.security.oauth2.provider.token.TokenStore;
import org.stringframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.stringframework.transaction.support.TransactionTemplate;

@RequiredArgsConstructor
@Slf4j
public class JdbcTokenStoreWithTx implements TokenStore {
    private final DataSource dataSource;
    private final TransactionTemplate tx;
    private JdbcTokenStore store;
    private ObjectMapper mapper;

    @PostConstruct
    private void init() {
        store = new JdbcTokenStore(dataSource);
        mapper = new ObjectMapper();
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return tx.execute(status -> store.readAuthentication(token));
    }
    
    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return tx.execute(status -> store.readAuthentication(token));
    }
    
    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        return tx.executeWithoutResult(status -> store.storeAccessToken(token, authentication));
    }
    
    @Override
    public OAuth2Authentication readAccessToken(String tokenValue) {
        return tx.execute(status -> store.readAccessToken(tokenValue));
    }
    
    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        return tx.executeWithoutResult(status -> store.removeAccessToken(token));
    }
    
    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        return tx.executeWithoutResult(status -> store.storeRefreshToken(refreshToken, authentication));
    }
    
    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return tx.execute(status -> store.readRefreshToken(tokenValue));
    }
    
    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken refreshToken) {
        return tx.execute(status -> store.readAuthenticationForRefreshToken(refreshToken));
    }
    
    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        return tx.executeWithoutResult(status -> store.removeRefreshToken(token));
    }
    
    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        return tx.executeWithoutResult(status -> store.removeAccessTokenUsingRefreshToken(refreshToken));
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return tx.execute(status -> store.getAccessToken(authentication));
    }
    
    @Override
    public Collection<OAuth2AccessToken> findTokenByClientIdAndUserName(String clientId, String username) {
        return tx.execute(status -> store.findTokenByClientIdAndUserName(clientId, username));
    }
    
    @Override
    public Collection<OAuth2AccessToken> findTokenBy(String clientId) {
        return tx.execute(status -> store.findTokenBy(clientId));
    }
}
