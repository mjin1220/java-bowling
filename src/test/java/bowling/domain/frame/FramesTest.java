package bowling.domain.frame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("프레임 리스트 테스트")
public class FramesTest {
    Frames frames = Frames.create();

    @DisplayName("프레임 리스트 생성")
    @Test
    public void createFrames() {
        assertThat(frames.getCurrentFrameNumber()).isEqualTo(1);
        assertThat(frames.isFinished()).isEqualTo(false);
    }

    @DisplayName("프레임 리스트에 스트라이크 추가")
    @Test
    public void record() {
        frames.record(10);

        assertThat(frames.getCurrentFrameNumber()).isEqualTo(2);
    }

    @DisplayName("프레임 리스트에 10보다 작은 점수 추가")
    @Test
    public void recordNotFinished() {
        frames.record(8);

        assertThat(frames.getCurrentFrameNumber()).isEqualTo(1);
    }

    @DisplayName("모든 프레임을 다 기록했을 때")
    @ParameterizedTest
    @CsvSource(value = {"10:false", "11:false", "12:true"}, delimiter = ':')
    public void isFinished(int tryCount, boolean isFinished) {
        record(tryCount);

        assertThat(frames.isFinished()).isEqualTo(isFinished);
    }

    private void record(int tryCount) {
        for (int i = 0; i < tryCount; i++) {
            frames.record(10);
        }
    }

    @DisplayName("10프레임 기록 후 또 기록할 때")
    @Test
    public void invalidRecord() {
        assertThatThrownBy(() -> {
            record(13);
        }).isInstanceOf(InvalidFrameRecordActionException.class);
    }
}