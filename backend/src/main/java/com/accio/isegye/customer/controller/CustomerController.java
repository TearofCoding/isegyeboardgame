package com.accio.isegye.customer.controller;

import com.accio.isegye.config.MqttConfig.MqttGateway;
import com.accio.isegye.customer.dto.CreateCustomerRequest;
import com.accio.isegye.customer.dto.CustomerResponse;
import com.accio.isegye.customer.service.CustomerService;
import com.accio.isegye.game.dto.GameListResponse;
import com.accio.isegye.game.dto.GameResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@Tag(name = "Customer", description = "Customer API")
public class CustomerController {

    private final CustomerService customerService;
    private final MqttGateway mqttGateway;

    @Operation(
        summary = "고객 시작",
        description = "고객 테이블 생성"
    )
    @PostMapping("/{roomId}")
    public ResponseEntity<CustomerResponse> createCustomer(@PathVariable int roomId, @Valid @RequestBody CreateCustomerRequest request){
        return new ResponseEntity<>(customerService.createCustomer(roomId, request), HttpStatus.CREATED);
    }

    @Operation(
        summary = "고객 끝",
        description = "고객의 사용시간 끝, 사용 요금을 반환한다, 보드게임 반환 요청을 보낸다"
    )
    @PatchMapping("/{customerId}")
    public ResponseEntity<Integer> endCustomer(@PathVariable int customerId){

        //터틀봇에게 반환 요청을 보낸다!
        int roomFee = customerService.endCustomer(customerId);
        return new ResponseEntity<>(roomFee, HttpStatus.OK);
    }

    @Operation(
        summary = "테마 on/off 토글",
        description = "고객의 방의 테마를 on/off를 변경한다"
    )
    @PatchMapping("/{customerId}/theme")
    public ResponseEntity<Integer> toggleTheme(@PathVariable int customerId){
        int themeUsed = customerService.toggleTheme(customerId);
        int roomId = customerService.findRoom(customerId);
        if(themeUsed == 0){ // turn off
            //디폴트 혹은 소리 제거
            mqttGateway.sendToMqtt("Default", "display/" + roomId);
        }else{
            //테마를 가져온다
            String themeType = customerService.getTheme(customerId);
            mqttGateway.sendToMqtt(themeType, "display/" + roomId);
        }
        return new ResponseEntity<>(themeUsed, HttpStatus.OK);
    }

    /*
    * 테마 음량 조절 기능
    * */
    @Operation(
        summary = "테마 음량 조절",
        description = "고객의 방의 음량을 조절한다"
    )
    @PostMapping("/{customerId}/sound")
    public ResponseEntity<?> changeVolume(@PathVariable int customerId, @RequestParam @NotBlank String volume){
        int roomId = customerService.findRoom(customerId);
        mqttGateway.sendToMqtt(volume, "display/" + roomId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
        summary = "고객과 테마 이미지 합성",
        description = "테마 이미지에 고객의 얼굴을 합성한다"
    )
    @PostMapping(path = "/{customerId}/swapface",
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> swapFace(@PathVariable Integer customerId, @RequestPart @NotNull MultipartFile sourceImg){

        String imageUrl = customerService.swapFace(customerId, sourceImg);
        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }

    @GetMapping(path = "/recommend")
    public ResponseEntity<?> getGameRecommendation(@RequestParam Integer gameId){
        List<GameResponse> gameList = customerService.getGameRecommendation(gameId);
        return new ResponseEntity<>(new GameListResponse(gameList), HttpStatus.OK);
    }

    @GetMapping(path  = "/filter")
    public ResponseEntity<GameListResponse> getGameRecommendationFilter(
        @NotBlank @RequestParam String theme,
        @NotBlank @RequestParam String difficulty,
        @NotBlank @RequestParam String tag,
        @NotBlank @RequestParam String time){

        List<GameResponse> gameList = customerService.getGameRecommendation(theme, difficulty, tag, time);

        return new ResponseEntity<>(new GameListResponse(gameList), HttpStatus.OK);
    }

}
