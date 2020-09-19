package ${package_pojo};
<#if swagger==true>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
import javax.persistence.*;
import java.io.Serializable;
<#list typeSet as set>
import ${set};
</#list>
/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
<#if swagger==true>
@ApiModel(description = "${Table}",value = "${Table}")
</#if>
@Table(name="${TableName}")
public class ${Table} implements Serializable{

<#list models as model>
	/**
	 * ${model.desc!""}
	 */
	<#if swagger==true>
	@ApiModelProperty(value = "${model.desc!""}",required = false)
	</#if>
	<#if model.id==true>
	@Id
	<#if model.identity=='YES'>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	</#if>
	</#if>
    @Column(name = "${model.column}")
	private ${model.simpleType} ${model.name};

</#list>


<#list models as model>
	public ${model.simpleType} get${model.upperName}() {
		return ${model.name};
	}

	public void set${model.upperName}(${model.simpleType} ${model.name}) {
		this.${model.name} = ${model.name};
	}

</#list>
}
