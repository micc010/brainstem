/**
 * Copyright 2018 http://github.com/micc010
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package ${package}.${moduleName}.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${package}.${moduleName}.entity.${className}Entity;
import ${package}.${moduleName}.service.${className}Service;
import ${mainPath}.common.utils.PageUtils;
import ${mainPath}.common.utils.R;

/**
 * ${comments}
 *
 * @author ${author}
 * @date ${datetime}
 */
@RestController
@RequestMapping("${moduleName}/${pathName}")
public class ${className}Controller {
    @Autowired
    private ${className}Service ${classname}Service;

    /**
     * 列表
     */
    @RequestMapping("/data")
    @RequiresPermissions("${moduleName}:${pathName}:data")
    public R data(@RequestParam Map<String, Object> params){
        PageUtils page = ${classname}Service.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{${pk.attrname}}")
    @RequiresPermissions("${moduleName}:${pathName}:info")
    public R info(@PathVariable("${pk.attrname}") ${pk.attrType} ${pk.attrname}){
			${className}Entity ${classname} = ${classname}Service.selectById(${pk.attrname});

        return R.ok().put("${classname}", ${classname});
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("${moduleName}:${pathName}:save")
    public R save(@RequestBody ${className}Entity ${classname}){
			${classname}Service.insert(${classname});

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("${moduleName}:${pathName}:update")
    public R update(@RequestBody ${className}Entity ${classname}){
			${classname}Service.updateById(${classname});

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("${moduleName}:${pathName}:delete")
    public R delete(@RequestBody ${pk.attrType}[] ${pk.attrname}s){
			${classname}Service.deleteBatchIds(Arrays.asList(${pk.attrname}s));

        return R.ok();
    }

}
