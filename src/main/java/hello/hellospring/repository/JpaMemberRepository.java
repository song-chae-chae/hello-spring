package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    private final EntityManager entityManager;

    public JpaMemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = entityManager.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        return entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList()
                .stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // jpql - 쿼리 언어, 멤버 엔티티 대상으로 쿼리 날리면 sql로 번역됨
        return entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
