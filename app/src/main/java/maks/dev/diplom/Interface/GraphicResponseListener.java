package maks.dev.diplom.Interface;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by berezyckiy on 2/20/17.
 */

public interface GraphicResponseListener {

    void onSuccessLoading(ArrayList<Map<String, String>> data);

    void onErrorLoading();

}
