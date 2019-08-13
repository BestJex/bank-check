package com.cmb.bankcheck.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.cmb.bankcheck.comon.TaskFactory;
import com.cmb.bankcheck.config.AppConfig;
import com.cmb.bankcheck.config.NewConfig;
import com.cmb.bankcheck.entity.ProcessEntity;
import com.cmb.bankcheck.entity.TaskEntity;
import com.cmb.bankcheck.mapper.ProcessMapper;
import com.cmb.bankcheck.message.Message;
import com.cmb.bankcheck.message.ResponseMessage;
import com.cmb.bankcheck.service.ApprovalService;
import com.cmb.bankcheck.task.ApprovalServiceAbstracter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.form.TaskElContext;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.lang.model.element.NestingKind;
import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-01
 * Time:13:07
 * 1.员工根据自己姓名（员工id)查询自己的任务，返回自己的姓名（id),代办的任务id,任务ins_id,任务名称
 * 2.
 */
@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private AppConfig config;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private NewConfig newConfig;

    @Resource(name="personTask")
    private ApprovalServiceAbstracter approvalService;


    @Override
    public Message queryTask(String assignee) {
        if (assignee==null){
            Message msg=new Message();
            msg.setMsg("字段不全");
            msg.setStatus(config.getErrorCode());
            return msg;
        }

        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskAssignee(assignee);
        List<Task> taskList = taskQuery.list();
        ResponseMessage<TaskEntity> resMsg=new ResponseMessage<>();
        List<TaskEntity> data=new ArrayList<>();
        for (Task task:taskList) {
            TaskEntity entity=new TaskEntity();
            entity.setAssignee(assignee);
            //返回group
            entity.setGroupId(task.getCategory());
            entity.setTaskId(task.getId());
            entity.setUpdateTime(task.getCreateTime());
            entity.setTaskName(task.getName());
            entity.setProcessInstanceId(task.getProcessInstanceId());
            data.add(entity);
        }
        resMsg.setData(data);
        resMsg.setSize(data.size());
        resMsg.setStatus(config.getSuccessCode());
        resMsg.setMsg(config.getSuccessMsg());
        return resMsg;
    }

    @Override
    public Message startTask(String taskId,String judgement,String remark,String assignee) {
          /*根据传进来的任务id，以及审批人自己的决定来确定是否同意任务。
           *前段传进的参数包括，待执行任务id,决定：不/同意,审批意见，指定下一个任务审批人
           *1.删除当前任务
           *2.并更新历史数据*/

        if (taskId==null || judgement==null ||assignee==null){
            Message msg=new Message();
            msg.setMsg("字段不全");
            msg.setStatus(config.getErrorCode());
            return msg;
        }

        Message msg=new Message();
        ResponseMessage<ProcessEntity> remsg = new ResponseMessage<>();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId=task.getProcessInstanceId();
        String label =task.getDescription();
        System.out.println("该任务为："+label+"任务");

        //会签任务
        if(label!=null && label.equals("sign")){
             System.out.println("启动会签任务");
             ApprovalServiceAbstracter multiTask= TaskFactory.createTask("multiTask");
             System.out.println("监测到了会签任务，启动成功");
             return multiTask.startTask( taskId,judgement,remark,assignee);
        }else{
            //非会签任务
            System.out.println("启动普通任务");
            ApprovalServiceAbstracter personTask=TaskFactory.createTask("personTask");
            System.out.println("监测到了普通任务，启动成功");
            return personTask.startTask( taskId,judgement,remark,assignee);
        }
    }

    @Override
    public Message roleManagement(String name, String group) {
        return null;
    }

}