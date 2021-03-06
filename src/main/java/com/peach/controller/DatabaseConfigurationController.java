package com.peach.controller;

import com.peach.model.DatabaseConfiguration;
import com.peach.service.DatabaseConfigurationService;
import com.peach.vo.common.Result;
import com.peach.vo.sql.SqlMetaData;
import com.peach.vo.sql.SqlStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 刘宇泽
 * @date 2018/7/5
 */
@RestController
@RequestMapping("admin_api/v1/")
public class DatabaseConfigurationController {

  @Autowired
  private DatabaseConfigurationService databaseConfigurationService;

  /**
   * 添加数据库配置
   *
   * @param databaseConfiguration
   * @param bindingResult
   * @return
   */
  @PostMapping("database_configuration")
  public Result saveDatabaseConfiguration(@Valid @RequestBody DatabaseConfiguration databaseConfiguration, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String error = bindingResult.getFieldErrors().stream()
          .map(FieldError::getDefaultMessage)
          .collect(Collectors.joining(" "));
      return Result.fail(error);
    }
    DatabaseConfiguration result = databaseConfigurationService.save(databaseConfiguration);
    return Result.succ(result);
  }

  /**
   * 修改数据库配置
   *
   * @param id
   * @param databaseConfiguration
   * @param bindingResult
   * @return
   */
  @PutMapping("database_configuration/{id}")
  public Result updateDatabaseConfiguration(@PathVariable("id") Integer id,
                                            @Valid @RequestBody DatabaseConfiguration databaseConfiguration, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String error = bindingResult.getFieldErrors().stream()
          .map(FieldError::getDefaultMessage)
          .collect(Collectors.joining(" "));
      return Result.fail(error);
    }
    DatabaseConfiguration result = databaseConfigurationService.update(id, databaseConfiguration);
    return Result.succ(result);
  }

  /**
   * 获取数据库配置详情
   *
   * @param id
   * @return
   */
  @GetMapping("database_configuration/{id}")
  public Result getDatabaseConfiguration(@PathVariable("id") Integer id) {
    DatabaseConfiguration result = databaseConfigurationService.getDatabaseConfigurationInfo(id);
    return Result.succ(result);
  }

  /**
   * 获取数据库元数据
   *
   * @param id
   * @return
   */
  @GetMapping("database_configuration/{id}/metadata")
  public Result getDatabaseConfigurationMetadata(@PathVariable("id") Integer id) {
    Map<String, List<SqlMetaData>> result = databaseConfigurationService.getDatabaseConfigurationMetadata(id);
    return Result.succ(result);
  }

  /**
   * 删除数据库配置
   *
   * @param id
   * @return
   */
  @DeleteMapping("database_configuration/{id}")
  public Result deleteDatabaseConfiguration(@PathVariable("id") Integer id) {
    databaseConfigurationService.deleteDatabaseConfigurationInfo(id);
    return Result.succ();
  }

  /**
   * 数据库配置列表
   *
   * @return
   */
  @GetMapping("database_configurations")
  public Result listDatabaseConfiguration() {
    List<DatabaseConfiguration> databaseConfigurations = databaseConfigurationService.listDatabaseConfiguration();
    return Result.succ(databaseConfigurations);
  }

  /**
   * 执行脚本
   *
   * @param sqlStatement
   * @param bindingResult
   * @return
   */
  @PostMapping("database_configurations/execute")
  public Result executeSql(@Valid @RequestBody SqlStatement sqlStatement, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String error = bindingResult.getFieldErrors().stream()
          .map(FieldError::getDefaultMessage)
          .collect(Collectors.joining(" "));
      return Result.fail(error);
    }
    Object result = databaseConfigurationService.executeSql(sqlStatement);
    return Result.succ(result);
  }

}
