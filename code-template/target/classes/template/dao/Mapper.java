package ${package_mapper};
import ${package_pojo}.${Table};
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Repository
public interface ${Table}Mapper extends Mapper<${Table}> {
}
