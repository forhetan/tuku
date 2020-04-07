package cn.picturecool.service.picture;

import cn.picturecool.DTO.PictureMainDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-02-28 15:01
 **/
public interface PictureMainService {
    PictureMainDTO findPictureByUniqueHash(String uniqueHash);

    List<PictureMainDTO> selectAll();

    List<PictureMainDTO> selectAllByTime();

    List<PictureMainDTO> selectAllByHot();

    List<PictureMainDTO> selectAllByLabel(String word);

    List<PictureMainDTO> selectAllByAdminUpload();

    IPage<PictureMainDTO> selectAllByLabelPage(Integer size, Integer current, String word);

    IPage<PictureMainDTO> selectAllByTimePage(Integer size, Integer current);

    IPage<PictureMainDTO> selectAllByHotPage(Integer size, Integer current);

    IPage<PictureMainDTO> selectAllPage(Integer size, Integer current);

    int insertMain(PictureMainDTO pictureMainDTO);

    int updateMainStyle(String uniqueHash, String pictureStyle);

    int updateMainLikeTotal(String uniqueHash, int likeTotal);

    int updateMainSearchTotal(String uniqueHash, int searchTotal);

    int updateMainDownloadTotal(String uniqueHash, int downloadTotal);

    int updateByNewUpload(String uniqueHash, int uploadTotal, Long lastUploadUserId, LocalDateTime lastUploadDate, String pictureStyle);

    int updateUploadTotalAndUploadUser(String uniqueHash, int uploadTotal, Long lastUploadUserId, LocalDateTime lastUploadDate);

    int updateUploadTotal(String uniqueHash, int uploadTotal);

    int deleteByUniqueHash(String uniqueHash);

    void uploadWeightsByUniqueHash(String uniqueHash);
}
