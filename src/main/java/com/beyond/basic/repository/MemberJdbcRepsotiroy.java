package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJdbcRepsotiroy implements MemberRepository{
    // Datasource는 DB와 JDBC에서 사용하는 DB 연결 드라이버 객체
    // application.yml에서 설정한 DB 정보가 자동으로 저장
    @Autowired
    private DataSource dataSource;

    @Override
    public Member save(Member member) {
        try {
            Connection con = dataSource.getConnection();
            String sql = "insert into member (name,email,password)values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPassword());
            ps.executeUpdate(); // 추가 수정의 경우 executeUpdate . 조회의 경우 excuteQuery
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Member> findAll() {
        List<Member> memberList = new ArrayList<>();
        try {
            Connection con = dataSource.getConnection();
            String sql = "select * from member";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member member = new Member();

                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPassword(rs.getString("password"));
                memberList.add(member);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberList;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = new Member();
        try {
            Connection con = dataSource.getConnection();
            String sql = "select * from member where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(member);
    }
}
