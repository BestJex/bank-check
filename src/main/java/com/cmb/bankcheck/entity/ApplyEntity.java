package com.cmb.bankcheck.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-31
 * Time:11:20
 *  客户申请实体类
 */
public class ApplyEntity {

    private String userId;

    private String username;

    private String msg;

    private String processKey;

    // 发起人（员工）
    private String starter;
    // 给当前申请分配的id
    private String applyId;

    // 减免类型
    private String xmtype;

    // 减免金额
    private double amt;

    // 本年度的减免记录
    private String record;

    // 合作前景
    private String cor;

    // 效益评估与分析
    private String analyse;

    // 客户规模，效益有无不良贷款
    private String situation;

    // 办理机构(分行)
    private String branch;

    // 办理网点（支行）
    private String subbranch;

    // 减免类型
    private String discountType;

    public String getSubbranch() {
        return subbranch;
    }

    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getApplyId() {
        return applyId;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public String getXmtype() {
        return xmtype;
    }

    public void setXmtype(String xmtype) {
        this.xmtype = xmtype;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getAnalyse() {
        return analyse;
    }

    public void setAnalyse(String analyse) {
        this.analyse = analyse;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String createApplyId(String userId, String processId){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        return userId + processId + newDate;
    }
}
