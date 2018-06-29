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
package ${package}.${moduleName}.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import ${mainPath}.common.utils.PageUtils;
import ${mainPath}.common.utils.Query;

import ${package}.${moduleName}.dao.${className}Dao;
import ${package}.${moduleName}.entity.${className}Entity;
import ${package}.${moduleName}.service.${className}Service;

/**
 * ${comments}
 *
 * @author ${author}
 * @date ${datetime}
 */
@Service("${classname}Service")
public class ${className}ServiceImpl extends ServiceImpl<${className}Dao, ${className}Entity> implements ${className}Service {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<${className}Entity> page = this.selectPage(
                new Query<${className}Entity>(params).getPage(),
                new EntityWrapper<${className}Entity>()
        );

        return new PageUtils(page);
    }

}
