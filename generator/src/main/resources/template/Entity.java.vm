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
package ${package}.${moduleName}.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

#if(${hasBigDecimal})
import java.math.BigDecimal;
#end
import java.io.Serializable;
import java.util.Date;

/**
 * ${comments}
 * 
 * @author ${author}
 * @date ${datetime}
 */
@TableName("${tableName}")
public class ${className}Entity implements Serializable {
	private static final long serialVersionUID = 1L;

#foreach ($column in $columns)
	/**
	 * $column.comments
	 */
	#if($column.columnName == $pk.columnName)
@TableId
	#end
private $column.attrType $column.attrname;
#end

#foreach ($column in $columns)
	/**
	 * 设置：${column.comments}
	 */
	public void set${column.attrName}($column.attrType $column.attrname) {
		this.$column.attrname = $column.attrname;
	}
	/**
	 * 获取：${column.comments}
	 */
	public $column.attrType get${column.attrName}() {
		return $column.attrname;
	}
#end
}
