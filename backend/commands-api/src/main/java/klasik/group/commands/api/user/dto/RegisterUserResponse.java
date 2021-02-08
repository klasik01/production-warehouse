package klasik.group.commands.api.user.dto;

import klasik.group.core.dto.BaseResponse;

/**
 * RegisterUserResponse
 *
 * @author pc00275
 * @since 06.02.2021
 */
public class RegisterUserResponse extends BaseResponse {

    private String id;

    public RegisterUserResponse(String id, String message) {
        super(message);
        this.id = id;
    }
}
