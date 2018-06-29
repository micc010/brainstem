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
package ${package}.${moduleName}.service;

import com.baomidou.mybatisplus.service.IService;
import ${mainPath}.common.utils.PageUtils;
import ${package}.${moduleName}.entity.${className}Entity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author ${author}
 * @date ${datetime}
 */
public interface ${className}Service extends IService<${className}Entity> {

    PageUtils queryPage(Map<String, Object> params);
}

