package com.mprv.audit_recorder;

import com.mprv.automation.operations.postJelly.eAuditPhaseTwo_parameters;

import java.util.Arrays;
import java.util.List;

public class StabilityAuditRawDo {


    private static List<eAuditPhaseTwo_parameters> XMLAuditParams = Arrays.asList(
            eAuditPhaseTwo_parameters.dbInstance,
            eAuditPhaseTwo_parameters.operation,
            eAuditPhaseTwo_parameters.object,
            eAuditPhaseTwo_parameters.exceptionOccurred,
            eAuditPhaseTwo_parameters.applicationUser,
            eAuditPhaseTwo_parameters.schema,
            eAuditPhaseTwo_parameters.eventType,
            eAuditPhaseTwo_parameters.privileged,
            eAuditPhaseTwo_parameters.objectType,
            eAuditPhaseTwo_parameters.normalizedQuery,
            eAuditPhaseTwo_parameters.serviceType,
            eAuditPhaseTwo_parameters.sourceOfActivity,
            eAuditPhaseTwo_parameters.dbUser,
            eAuditPhaseTwo_parameters.destinationPort,
            eAuditPhaseTwo_parameters.originalUserName,
            eAuditPhaseTwo_parameters.rawQuery,
            eAuditPhaseTwo_parameters.exceptionString,
            eAuditPhaseTwo_parameters.bindVars,
            eAuditPhaseTwo_parameters.database,
            eAuditPhaseTwo_parameters.eventID,
            eAuditPhaseTwo_parameters.destinationIp,
            eAuditPhaseTwo_parameters.responseSizeSum,
            eAuditPhaseTwo_parameters.affectedRowsSum,
            eAuditPhaseTwo_parameters.sourceIp
    );

    private static List<eAuditPhaseTwo_parameters> JsonAuditParams = Arrays.asList(

            eAuditPhaseTwo_parameters.operation,
            eAuditPhaseTwo_parameters.object,
            eAuditPhaseTwo_parameters.exceptionOccurred,
            eAuditPhaseTwo_parameters.hits,
            eAuditPhaseTwo_parameters.applicationUser,
            eAuditPhaseTwo_parameters.schema,
            eAuditPhaseTwo_parameters.eventType,
            eAuditPhaseTwo_parameters.privileged,
            eAuditPhaseTwo_parameters.isSensitive,
            eAuditPhaseTwo_parameters.isStoredProcedure,
            eAuditPhaseTwo_parameters.osUser,
            eAuditPhaseTwo_parameters.objectType,
            eAuditPhaseTwo_parameters.normalizedQuery,
            eAuditPhaseTwo_parameters.serviceType,
            eAuditPhaseTwo_parameters.sourceApp,
            eAuditPhaseTwo_parameters.sourceOfActivity,
            eAuditPhaseTwo_parameters.subject,
            eAuditPhaseTwo_parameters.dbUser,
            eAuditPhaseTwo_parameters.isAuthenticated,
            eAuditPhaseTwo_parameters.destinationPort,
            eAuditPhaseTwo_parameters.originalUserName,
            eAuditPhaseTwo_parameters.rawQuery,
            eAuditPhaseTwo_parameters.queryGroup,
            eAuditPhaseTwo_parameters.exceptionString,
            eAuditPhaseTwo_parameters.bindVars,
            eAuditPhaseTwo_parameters.gatewayIdName,
            eAuditPhaseTwo_parameters.policy,
            eAuditPhaseTwo_parameters.database,
            eAuditPhaseTwo_parameters.service,
            eAuditPhaseTwo_parameters.applicationIdName,
            eAuditPhaseTwo_parameters.eventID,
            eAuditPhaseTwo_parameters.destinationIp,
            eAuditPhaseTwo_parameters.responseSizeSum,
            eAuditPhaseTwo_parameters.affectedRowsSum,
            eAuditPhaseTwo_parameters.sourceIp,
            eAuditPhaseTwo_parameters.operationType,
            eAuditPhaseTwo_parameters.host
    );

    private static List<eAuditPhaseTwo_parameters> allAuditParams = Arrays.asList(
        ////////////////////
//            eAuditPhaseTwo_parameters.affectedRowsSum,
//            eAuditPhaseTwo_parameters.dbInstance,
//            eAuditPhaseTwo_parameters.eventRealDateAndTime,
//            eAuditPhaseTwo_parameters.responseSizeSum,
//            eAuditPhaseTwo_parameters.sourcePort,
//            eAuditPhaseTwo_parameters.processId,
//            eAuditPhaseTwo_parameters.rawQuery,
//            eAuditPhaseTwo_parameters.exceptionString,
//            eAuditPhaseTwo_parameters.operation,
//            eAuditPhaseTwo_parameters.serviceType,
//            eAuditPhaseTwo_parameters.bindVars,
//            eAuditPhaseTwo_parameters.applicationUser,
//            eAuditPhaseTwo_parameters.normalizedQuery,
//            eAuditPhaseTwo_parameters.dbUser,
//            eAuditPhaseTwo_parameters.object,
//            eAuditPhaseTwo_parameters.destinationIp,
//            eAuditPhaseTwo_parameters.sourceIp,
//            eAuditPhaseTwo_parameters.database,
//            eAuditPhaseTwo_parameters.destinationPort,
//            eAuditPhaseTwo_parameters.sourceOfActivity,
//            eAuditPhaseTwo_parameters.service,
//            eAuditPhaseTwo_parameters.gatewayIdName,
//            eAuditPhaseTwo_parameters.hits,
//            eAuditPhaseTwo_parameters.exceptionOccurred,
//            eAuditPhaseTwo_parameters.host,
//            eAuditPhaseTwo_parameters.schema,
//            eAuditPhaseTwo_parameters.applicationIdName,
//            eAuditPhaseTwo_parameters.osUser,
//            eAuditPhaseTwo_parameters.sourceApp,
//            eAuditPhaseTwo_parameters.privileged,
//            eAuditPhaseTwo_parameters.operationType,
//            eAuditPhaseTwo_parameters.eventType,
//            eAuditPhaseTwo_parameters.isSensitive,
//            eAuditPhaseTwo_parameters.isStoredProcedure,
//            eAuditPhaseTwo_parameters.policy,
//            eAuditPhaseTwo_parameters.subject,
//            eAuditPhaseTwo_parameters.originalUserName,
//            eAuditPhaseTwo_parameters.queryGroup,
//            eAuditPhaseTwo_parameters.userGroup,
//            eAuditPhaseTwo_parameters.objectType,
//            eAuditPhaseTwo_parameters.eventID,
//            eAuditPhaseTwo_parameters.isAuthenticated,
//            eAuditPhaseTwo_parameters.userDefinedTag1
        //           eAuditPhaseTwo_parameters.rawQuery,
        //           eAuditPhaseTwo_parameters.exceptionString,
//            eAuditPhaseTwo_parameters.operation,
//            eAuditPhaseTwo_parameters.serviceType,
//            eAuditPhaseTwo_parameters.bindVars,
        //           eAuditPhaseTwo_parameters.applicationUser,
        //           eAuditPhaseTwo_parameters.normalizedQuery,
        //           eAuditPhaseTwo_parameters.dbUser,
//            eAuditPhaseTwo_parameters.nativeQuery,
        //           eAuditPhaseTwo_parameters.object,
        //           eAuditPhaseTwo_parameters.destinationIp,
//            eAuditPhaseTwo_parameters.sourceIp,
//            eAuditPhaseTwo_parameters.database,
//            eAuditPhaseTwo_parameters.destinationPort,
//            eAuditPhaseTwo_parameters.url,
//            eAuditPhaseTwo_parameters.webSourceIp,
//            eAuditPhaseTwo_parameters.sourceOfActivity,
//            eAuditPhaseTwo_parameters.userDefinedTag1,
//            eAuditPhaseTwo_parameters.service,
//            eAuditPhaseTwo_parameters.eventRealDateAndTime,
//            eAuditPhaseTwo_parameters.eventCreationTime,
//            eAuditPhaseTwo_parameters.customTags,
//            eAuditPhaseTwo_parameters.gateway,
//            eAuditPhaseTwo_parameters.gatewayIdName,
        //          eAuditPhaseTwo_parameters.hits,
//            eAuditPhaseTwo_parameters.exceptionOccurred,
//            eAuditPhaseTwo_parameters.host,
//            eAuditPhaseTwo_parameters.schema,
//            eAuditPhaseTwo_parameters.responseSizeSum,
//            eAuditPhaseTwo_parameters.applicationIdName,
        //           eAuditPhaseTwo_parameters.osUser,
        //           eAuditPhaseTwo_parameters.sourceApp,
//            eAuditPhaseTwo_parameters.privileged,
        //           eAuditPhaseTwo_parameters.operationType,
//            eAuditPhaseTwo_parameters.eventType,
//            eAuditPhaseTwo_parameters.isSensitive,
        //           eAuditPhaseTwo_parameters.isStoredProcedure,
//            eAuditPhaseTwo_parameters.policy,
//            eAuditPhaseTwo_parameters.subject,
//            eAuditPhaseTwo_parameters.affectedRowsSum,
//            eAuditPhaseTwo_parameters.originalUserName,
//            eAuditPhaseTwo_parameters.queryGroup,

//FAM based
//            eAuditPhaseTwo_parameters.famSourceIp,
//            eAuditPhaseTwo_parameters.famDestinationIp,
//            eAuditPhaseTwo_parameters.userType,
//            eAuditPhaseTwo_parameters.userName,
//            eAuditPhaseTwo_parameters.userDomain,
//            eAuditPhaseTwo_parameters.path,
//            eAuditPhaseTwo_parameters.fullPath,
//            eAuditPhaseTwo_parameters.file,
//            eAuditPhaseTwo_parameters.secondFileName,
//            eAuditPhaseTwo_parameters.extension,
//            eAuditPhaseTwo_parameters.isFolder,
//            eAuditPhaseTwo_parameters.isErrorOccured,
//            eAuditPhaseTwo_parameters.isInsufficientRights,
//            eAuditPhaseTwo_parameters.classification,
//            eAuditPhaseTwo_parameters.folder,
//            eAuditPhaseTwo_parameters.userDepartment,
//            eAuditPhaseTwo_parameters.objectType,
//            eAuditPhaseTwo_parameters.osUserOwner,
//            eAuditPhaseTwo_parameters.osGroupOwner,
////          eAuditPhaseTwo_parameters.responseInfo,
        //           eAuditPhaseTwo_parameters.eventID,
//            eAuditPhaseTwo_parameters.sourcePort,
        //           eAuditPhaseTwo_parameters.isAuthenticated,
        //          eAuditPhaseTwo_parameters.userDefinedTag1
//            eAuditPhaseTwo_parameters.processId,
//            eAuditPhaseTwo_parameters.processPath,
//            eAuditPhaseTwo_parameters.processArguments,
//            eAuditPhaseTwo_parameters.firstOpen,
//            eAuditPhaseTwo_parameters.lastClose,
//            eAuditPhaseTwo_parameters.secondExtension,
////          eAuditPhaseTwo_parameters.isRename,
//            eAuditPhaseTwo_parameters.agent
//            eAuditPhaseTwo_parameters.secondPath,
//            eAuditPhaseTwo_parameters.errorId,
//            eAuditPhaseTwo_parameters.errorCode

);

    private static List<eAuditPhaseTwo_parameters> famAuditParams = Arrays.asList(
            eAuditPhaseTwo_parameters.operation,
            eAuditPhaseTwo_parameters.file,
            eAuditPhaseTwo_parameters.userName,
            eAuditPhaseTwo_parameters.sourceOfActivity,
            eAuditPhaseTwo_parameters.famSourceIp,
            eAuditPhaseTwo_parameters.isFolder
    );

    public static List<eAuditPhaseTwo_parameters> getAllAuditParams(){
        return allAuditParams;
    }

    public static List<eAuditPhaseTwo_parameters> getXMLAuditParams(){
        return XMLAuditParams;
    }

    public static List<eAuditPhaseTwo_parameters> getJsonAuditParams(){
        return JsonAuditParams;
    }

    public static List<eAuditPhaseTwo_parameters> getFamAuditParams() {
        return famAuditParams;
    }
}
