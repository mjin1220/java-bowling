package qna.domain;

import org.hibernate.annotations.Where;
import qna.exception.CannotDeleteException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Embeddable
public final class Answers {

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private final List<Answer> answers;

    public Answers() {
        this(new ArrayList<>());
    }

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public int size() {
        return answers.size();
    }

    public List<Answer> answers() {
        return Collections.unmodifiableList(answers);
    }

    public DeleteHistories deleteAll(User user) {
        validateDeletableAnswers(user);
        return deleteAllAnswers(user);
    }

    private void validateDeletableAnswers(User user) {
        for (Answer answer : answers) {
            validateDeletableAnswer(answer, user);
        }
    }

    private void validateDeletableAnswer(Answer answer, User user) {
        if (!answer.isDeletable(user)) {
            throw new CannotDeleteException(CannotDeleteException.DIFFERENT_USERS_ANSWER_CONTAINED);
        }
    }

    private DeleteHistories deleteAllAnswers(User user) {
        final DeleteHistories deleteHistories = new DeleteHistories();
        for (Answer answer : answers) {
            deleteHistories.add(answer.delete(user));
        }
        return deleteHistories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answers answers1 = (Answers) o;
        return Objects.equals(answers, answers1.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answers);
    }
}
