package com.petdiary.core.exception;

import com.petdiary.core.dto.ComResultDto;
import com.petdiary.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ExceptionInfoConfig {
    private final Map<String, Object> exceptionInfo;
    private final ComResultDto successResultDto;
    private final ComResultDto undefinedErrorResult;

    @SuppressWarnings("unchecked")
    public ExceptionInfoConfig(String filePath) {
        log.info("[Configuration]: Exception Info Loading Start...");

        YamlMapFactoryBean yaml = new YamlMapFactoryBean();
        yaml.setResources(new ClassPathResource(filePath));
        this.exceptionInfo = yaml.getObject();
        Map<String, Object> successInfos = (Map<String, Object>) Objects.requireNonNull(exceptionInfo).get("success");
        Map<String, Object> successInfo = (Map<String, Object>) successInfos.get("200ok");

        successResultDto = new ComResultDto();
        successResultDto.setCode(StringUtil.ifNullToEmpty(successInfo.get("code")));
        successResultDto.setMessage(StringUtil.ifNullToEmpty(successInfo.get("desc")));
        successResultDto.setStatus(StringUtil.ifNullToEmpty(successInfo.get("status")));

        Map<String, Object> exceptionInfos = (Map<String, Object>) exceptionInfo.get("exception");
        Map<String, Object> exceptionInfo = (Map<String, Object>) exceptionInfos.get("not_define");

        undefinedErrorResult = new ComResultDto();
        undefinedErrorResult.setCode(StringUtil.ifNullToEmpty(exceptionInfo.get("code")));
        undefinedErrorResult.setMessage(StringUtil.ifNullToEmpty(exceptionInfo.get("desc")));
        undefinedErrorResult.setStatus(StringUtil.ifNullToEmpty(exceptionInfo.get("status")));

        log.info("[Configuration]: Exception Info Loading End...!");
    }

    public Map<String, Object> getExceptionInfo() {
        return exceptionInfo;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getExceptionInfo(String ymlKey) {
        Map<String, Object> exceptionInfos = (Map<String, Object>) exceptionInfo.get("exception");
        return (Map<String, Object>) exceptionInfos.get(ymlKey);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getSuccessInfo(String ymlKey) {
        Map<String, Object> successInfos = (Map<String, Object>) exceptionInfo.get("success");
        return (Map<String, Object>) successInfos.get(ymlKey);
    }

    public ComResultDto getSuccessResult() {
        return this.successResultDto;
    }

    public ComResultDto getUndefinedErrorResult() {
        return this.undefinedErrorResult;
    }

    public ComResultDto getSuccessInfoResult(String ymlKey) {
        Map<String, Object> exceptionInfo = getSuccessInfo(ymlKey);

        ComResultDto result = new ComResultDto();
        result.setCode(String.valueOf(exceptionInfo.get("code")));
        result.setMessage((String) exceptionInfo.get("desc"));
        result.setStatus(String.valueOf(exceptionInfo.get("status")));

        return result;

    }

    public ComResultDto getResultDto(String ymlKey) {
        Map<String, Object> exceptionInfo = getExceptionInfo(ymlKey);
        if (exceptionInfo == null) {
            return getUndefinedErrorResult();
        }
        ComResultDto resultDesc = new ComResultDto();
        resultDesc.setCode(StringUtil.ifNullToEmpty(exceptionInfo.get("code")));
        resultDesc.setMessage(StringUtil.ifNullToEmpty(exceptionInfo.get("desc")));
        resultDesc.setStatus(StringUtil.ifNullToEmpty(exceptionInfo.get("status")));
        return resultDesc;
    }

    public ComResultDto getResultDto(String ymlKey, Object[] args) {
        ComResultDto resultDesc = getResultDto(ymlKey);
        String desc = StringUtil.ifNullToEmpty(resultDesc.getMessage());
        resultDesc.setMessage(MessageFormat.format(desc, args));
        return resultDesc;
    }
}
