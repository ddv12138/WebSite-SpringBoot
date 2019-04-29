package ORM.Provider;

import ORM.POJO.ResourceTable;
import ORM.Utils.Condition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class ResourceSqlProvider {
    public String updateByExample(ResourceTable res) {
        return new SQL() {{
            UPDATE("Resourcetable");
            if (null != res.getName()) SET("name=#{name}");
            if (null != res.getCnname()) SET("cnname=#{cnname}");
            if (null != res.getIstop()) SET("istop=#{istop}");
            if (null != res.getLeftvalue()) SET("leftvalue=#{leftvalue}");
            if (null != res.getRightvalue()) SET("rightvalue=#{rightvalue}");
            WHERE("id=#{id}");
        }}.toString();
    }

    public String selectByExample(ResourceTable res) {
        return new SQL() {{
            SELECT("*");
            FROM("Resourcetable");
            if (null != res.getId()) WHERE("id=#{id}");
            if (null != res.getName()) WHERE("name=#{name}");
            if (null != res.getCnname()) WHERE("cnname=#{cnname}");
            if (null != res.getIstop()) WHERE("istop=#{istop}");
            if (null != res.getLeftvalue()) WHERE("leftvalue=#{leftvalue}");
            if (null != res.getRightvalue()) WHERE("rightvalue=#{rightvalue}");
        }}.toString();
    }

    public String updateByCondition(@Param("res") ResourceTable res, List<Condition> conds) {
        return new SQL() {{
            UPDATE("Resourcetable");
            if (null != res.getName()) SET("name=#{res.name}");
            if (null != res.getCnname()) SET("cnname=#{res.cnname}");
            if (null != res.getIstop()) SET("istop=#{res.istop}");
            if (null != res.getLeftvalue()) SET("leftvalue=#{res.leftvalue}");
            if (null != res.getRightvalue()) SET("rightvalue=#{res.rightvalue}");
            for (Condition con : conds) {
                WHERE(con.toString());
            }
        }}.toString();
    }

    public String selectByCondition(List<Condition> conds) {
        return new SQL() {{
            SELECT("*");
            FROM("Resourcetable");
            for (Condition con : conds) {
                WHERE(con.toString());
            }
        }}.toString();
    }
}
