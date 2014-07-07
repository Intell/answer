//package com.intell.lesson.auth.service;
//
//
//import com.sun.javaws.CacheUtil;
//import com.sun.xml.internal.bind.v2.model.core.ID;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.map.HashedMap;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authc.AccountException;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.crypto.RandomNumberGenerator;
//import org.apache.shiro.crypto.SecureRandomNumberGenerator;
//import org.apache.shiro.crypto.hash.Md5Hash;
//import org.apache.shiro.crypto.hash.Sha1Hash;
//import org.apache.shiro.crypto.hash.Sha256Hash;
//import org.apache.shiro.util.ByteSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.*;
//
///**
// * 账户管理业务逻辑
// *
// * @author vincent
// */
//public class AccountService {
//
//    @Autowired
//    private IPermissionService permissionService;
//
//    @Autowired
//    private IRoleService roleService;
//
//    private static Logger logger = LoggerFactory.getLogger(com.intell.lesson.auth.service.impl.AccountService.class);
//
//    // authorization的缓存名字
//    private String authorizationCacheName = "author-Cache";
//
//    // 页面上的password组件名
//    private String pagePasswordKey = "plainPassword";
//
//    // 页面上的username组件名
//    private String pageUserNameKey = "username";
//
//    // 页面上的fullname组件名
//    private String pageFullNameKey = "fullName";
//
//    HashedCredentialsMatcher hashedCredentialsMatcher;
//
//
//    @Override
//    public void setHashedCredentialsMatcher(HashedCredentialsMatcher hashedCredentialsMatcher) {
//        this.hashedCredentialsMatcher = hashedCredentialsMatcher;
//    }
//
//
//    @Override
//    public boolean confirmDifferentIpLogin(String userName, String host) {
//        return true;
//    }
//
//    // ------------------------------用户管理-----------------------------------//
//
//    /**
//     * 设置当前用户密码
//     */
//    private void setUserPassword(Map userMap) {
//        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
//        ByteSource salt = rng.nextBytes();
//        userMap.put(prop("user.salt"), EncodesUtil.encodeBase64(salt.getBytes()));
//        String hashPassword = entryptPassword(userMap.get(pagePasswordKey).toString(), salt);
//        userMap.put(prop("user.password"), hashPassword);
//
//    }
//
//    @Override
//    public void updateUserPassword(String oldPassword, String newPassword) {
//        BasicDBObject user = baseMongoService.fetchById(prop("user"), SessionHolder.getUserId());
//        String encodedSalt = user.getString(prop("user.salt"));
//        byte[] salt;
//        salt = EncodesUtil.decodeBase64(encodedSalt);
//        String oldHashPassword = entryptPassword(oldPassword, salt);
//        if (!user.getString("user.password").equals(oldHashPassword)) {
//            throw new BusinessException("旧密码不正确.");
//        }
//        Map userMap = new HashedMap();
//        userMap.put(pagePasswordKey, newPassword);
//        setUserPassword(userMap);
//        user.put(prop("user.password"), userMap.get(prop("user.password")).toString());
//        user.put(prop("user.salt"), userMap.get(prop("user.salt")).toString());
//
//        baseMongoService.refresh(prop("user"), getOID(user), user);
//    }
//
//    /**
//     * 通过id获取用户实体
//     *
//     * @param id 用户id
//     */
//    @Override
//    public BasicDBObject fetchUser(String id) {
//        return baseMongoService.fetchById(prop("user"), id);
//    }
//
//    @Override
//    public boolean removeUser(String id) {
//        WriteResult wr = baseMongoService.removeById(prop("user"), id);
//        return StringUtils.isEmpty(wr.getError());
//    }
//
//    /**
//     * 新增用户
//     *
//     * @param userMap 用户实体
//     */
//    @Override
//    public void addUser(Map userMap) {
//        if (isUsernameExists(userMap.get(pageUserNameKey).toString())) {
//            throw new AccountException("用户名已存在");
//        }
//        // 如果没有设定密码，则设定默认密码为_q1w2e3r4
//        if (!userMap.containsKey(pagePasswordKey)) {
//            userMap.put(pagePasswordKey, "_q1w2e3r4");
//        }
//        setUserPassword(userMap);
//        userMap.remove(pagePasswordKey);
//        if (!pageUserNameKey.equals(prop("user.userName"))) {
//            userMap.put(prop("user.userName"), userMap.get(pageUserNameKey));
//            userMap.remove(pageUserNameKey);
//        }
//
//        if (!pageFullNameKey.equals(prop("user.fullName"))) {
//            userMap.put(prop("user.fullName"), userMap.get(pageFullNameKey));
//            userMap.remove(pageFullNameKey);
//        }
//
//        userMap.put(prop("user.registeredDate"), new Date());
//
//        baseMongoService.store(prop("user"), userMap);
//
//        // todo:
//
//    }
//
//    /**
//     * 更新用户 并更新缓存
//     *
//     * @param userMap 用户实体
//     */
//    @Override
//    public void refreshUser(Map userMap) {
//        userMap.put(ID, SessionHolder.getUserId());
//        userMap.put(prop("user.updateDate"), new Date());
//        baseMongoService.store(prop("user"), userMap);
//        // todo:要跟踪核实shiro实际保存的key和值
//        CacheUtil.getAuthorizationCache().put(SessionHolder.getUserId(), userMap);
//        logger.debug("清除用户 {} 授权缓存成功！", SessionHolder.getUserId());
//    }
//
//    /**
//     * 是否唯一的用户名 如果是返回true,否则返回false
//     *
//     * @param username 用户名
//     * @return boolean
//     */
//    @Override
//    public boolean isUsernameExists(String username) {
//        return baseMongoService.fetchExists(prop("user"), prop("user.userName"), username);
//    }
//
//    /**
//     * 通过用户名获取用户实体
//     *
//     * @param username 用户实体
//     * @return User Object
//     */
//    @Override
//    public BasicDBObject fetchUserByUsername(String username) {
//        if (username == null) {
//            return null;
//        }
//        List returnKeys = new ArrayList();
//        returnKeys.add(ID);
//        returnKeys.add(prop("user.userName"));
//        returnKeys.add(prop("user.salt"));
//        returnKeys.add(prop("user.password"));
//        returnKeys.add(prop("user.host"));
//        DBCursor user = baseMongoService.fetch(prop("user"), prop("user.userName"), username, returnKeys);
//        if (user == null || user.size() != 1) {
//            return null;
//        } else {
//            return new BasicDBObject(user.next().toMap());
//        }
//    }
//
//    private String entryptPassword(String textPassword, Object salt) {
//
//        String hashPassword = null;
//        if (hashedCredentialsMatcher.getHashAlgorithmName().equalsIgnoreCase("MD5")) {
//            Md5Hash md5Hash = new Md5Hash(textPassword.getBytes(), salt, hashedCredentialsMatcher.getHashIterations());
//            if (hashedCredentialsMatcher.isStoredCredentialsHexEncoded()) {
//                hashPassword = md5Hash.toHex();
//            } else {
//                hashPassword = md5Hash.toBase64();
//            }
//        } else if (hashedCredentialsMatcher.getHashAlgorithmName().equalsIgnoreCase("SHA-1")) {
//            Sha1Hash sha1Hash = new Sha1Hash(textPassword.getBytes(), salt, hashedCredentialsMatcher.getHashIterations());
//            if (hashedCredentialsMatcher.isStoredCredentialsHexEncoded()) {
//                hashPassword = sha1Hash.toHex();
//            } else {
//                hashPassword = sha1Hash.toBase64();
//            }
//        } else if (hashedCredentialsMatcher.getHashAlgorithmName().equalsIgnoreCase("SHA-256")) {
//            Sha256Hash sha256Hash = new Sha256Hash(textPassword.getBytes(), salt, hashedCredentialsMatcher.getHashIterations());
//            if (hashedCredentialsMatcher.isStoredCredentialsHexEncoded()) {
//                hashPassword = sha256Hash.toHex();
//            } else {
//                hashPassword = sha256Hash.toBase64();
//            }
//        }
//
//        return hashPassword;
//    }
//
//    @Override
//    public void setAuthorizationCacheName(String authorizationCacheName) {
//        this.authorizationCacheName = authorizationCacheName;
//    }
//
//    // ------------------------------角色管理-----------------------------------//
//
//    @Override
//    public Set<DBObject> findRoles(String userId) {
//        Set<DBObject> roles = new HashSet<DBObject>();
//        BasicDBObject user = fetchUser(userId);
//        if (user.get(prop("user.role")) != null) {
//            if (user.get(prop("user.role")) instanceof String) {
//                List<DBObject> roleResults = roleService.findRolesByIds(Arrays.asList(new String[] { (String) user.get(prop("user.role")) }));
//                roles.addAll(roleResults);
//            } else if (user.get(prop("user.role")) instanceof String[]) {
//                List<DBObject> roleResults = roleService.findRolesByIds(Arrays.asList(user.get(prop("user.role"))));
//                roles.addAll(roleResults);
//            }else if( user.get(prop("user.role")) instanceof BasicDBList){
//                List<DBObject> roleResults = roleService.findRolesByIds((BasicDBList)user.get(prop("user.role")));
//                roles.addAll(roleResults);
//            }
//        }
//        return roles;
//    }
//
//    // ------------------------------权限管理-----------------------------------//
//    @Override
//    public Set<DBObject> findPermissions(String userId) {
//        Set<DBObject> permissions = new HashSet<DBObject>();
//        Set<DBObject> roles = findRoles(userId);
//        if (CollectionUtils.isNotEmpty(roles)) {
//            List<Object> permissionIds = new ArrayList<Object>();
//            for (DBObject role : roles) {
//                if (role.get(prop("role.permission")) != null) {
//                    if (role.get(prop("role.permission")) instanceof String) {
//                        permissionIds.add(role.get(prop("role.permission")).toString());
//                    } else if (role.get(prop("role.permission")) instanceof String[]) {
//                        permissionIds.addAll(Arrays.asList(role.get(prop("role.permission"))));
//                    }
//                }
//            }
//            List<DBObject> roleResults = permissionService.findResourceByIds(permissionIds);
//            permissions.addAll(roleResults);
//        }
//        return permissions;
//    }
//
//
//}
