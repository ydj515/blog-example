package com.example.proxyqueryplanexample.listener;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.proxy.ParameterSetOperation;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SlowQueryExplainListener implements QueryExecutionListener {

    private final long thresholdMs;
    private final DataSource dataSource;

    public SlowQueryExplainListener(long thresholdMs, DataSource dataSource) {
        this.thresholdMs = thresholdMs;
        this.dataSource = dataSource;
    }

    @Override
    public void beforeQuery(ExecutionInfo executionInfo, List<QueryInfo> list) {
        // 필요시 로깅 가능
    }

    @Override
    public void afterQuery(ExecutionInfo executionInfo, List<QueryInfo> queryInfoList) {
        long duration = executionInfo.getElapsedTime();
        if (duration > thresholdMs) {
            StringBuilder sqlBuilder = new StringBuilder();
            for (QueryInfo qi : queryInfoList) {
                sqlBuilder.append(qi.getQuery()).append(" ");
            }

            String sql = sqlBuilder.toString().trim();

            // 단순히 select 문에 대해서만 처리
            if (!sql.toLowerCase().startsWith("select")) return;

            // 바인딩된 SQL 생성
            String finalSql = bindParameters(sql, (queryInfoList.get(0).getParametersList().get(0)));

            System.out.println("⚠️ 느린 쿼리 (" + duration + "ms):\n" + finalSql);

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("EXPLAIN (ANALYZE) " + finalSql);
                 ResultSet rs = stmt.executeQuery()) {

                System.out.println("📊 실행 계획:");
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                }

            } catch (Exception e) {
                System.out.println("❗ EXPLAIN ANALYZE 실패: " + e.getMessage());
            }

        }
    }

    private String bindParameters(String sql, List<ParameterSetOperation> params) {
        if (params == null || params.isEmpty()) {
            return sql;
        }

        for (ParameterSetOperation paramOp : params) {
            Object value = null;
            Object[] args = paramOp.getArgs();

            // 보통 args[1]이 실제 바인딩 값임
            if (args != null && args.length > 1) {
                value = args[1];
            }

            String replacement;
            if (value == null) {
                replacement = "NULL";
            } else if (value instanceof Number) {
                replacement = value.toString();
            } else if (value instanceof Boolean) {
                replacement = ((Boolean) value) ? "TRUE" : "FALSE";
            } else {
                replacement = "'" + value.toString().replace("'", "''") + "'";
            }

            // 첫 번째 ?를 replacement로 치환
            sql = sql.replaceFirst("\\?", replacement);
        }

        return sql;
    }


}

