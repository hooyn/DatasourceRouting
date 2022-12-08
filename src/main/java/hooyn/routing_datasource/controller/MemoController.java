package hooyn.routing_datasource.controller;

import hooyn.routing_datasource.domain.Memo;
import hooyn.routing_datasource.repository.MemoRepository;
import hooyn.routing_datasource.repository.MemoRepositoryqdsl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoRepositoryqdsl memoRepositoryqdsl;
    private final MemoRepository memoRepository;

    @PostMapping("/memo")
    public ResponseEntity<?> insertMemo(@RequestParam String content) {
        Memo memo = Memo.builder().content(content).build();
        memoRepository.save(memo);
        return new ResponseEntity<>("Success Insert Memo Data", HttpStatus.OK);
    }

    @GetMapping("/memo")
    public ResponseEntity<?> selectMemo() {
        List<Memo> memos = memoRepositoryqdsl.searchMemo();
        return new ResponseEntity<>(memos, HttpStatus.OK);
    }
}
