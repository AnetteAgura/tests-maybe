package ee.bitweb.testingsample.domain.datapoint.api;

import java.util.List;
import javax.validation.Valid;

import ee.bitweb.testingsample.domain.datapoint.api.model.DataPointMapper;
import ee.bitweb.testingsample.domain.datapoint.api.model.DataPointPayload;
import ee.bitweb.testingsample.domain.datapoint.api.model.DataPointResponse;
import ee.bitweb.testingsample.domain.datapoint.features.FindAllDataPointsFeature;
import ee.bitweb.testingsample.domain.datapoint.features.GetDataPointByExternalIdFeature;
import ee.bitweb.testingsample.domain.datapoint.features.GetDataPointByIdFeature;
import ee.bitweb.testingsample.domain.datapoint.features.ImportDataPointsFeature;
import ee.bitweb.testingsample.domain.datapoint.features.update.UpdateDataPointFeature;
import ee.bitweb.testingsample.domain.datapoint.features.create.CreateDataPointFeature;

import static ee.bitweb.testingsample.domain.datapoint.api.DataPointController.BASE_URL;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class DataPointController {

    public static final String BASE_URL = "/data-points";

    private final CreateDataPointFeature createDataPointFeature;
    private final UpdateDataPointFeature updateDataPointFeature;
    private final GetDataPointByIdFeature getDataPointByIdFeature;
    private final GetDataPointByExternalIdFeature getDataPointByExternalIdFeature;
    private final FindAllDataPointsFeature findAllDataPointsFeature;
    private final ImportDataPointsFeature importDataPointsFeature;

    @GetMapping(value = "/{id}")
    public DataPointResponse getOneById(@PathVariable Long id) {
        return DataPointMapper.toResponse(getDataPointByIdFeature.get(id));
    }

    @GetMapping(value = "/external-id/{externalId}")
    public DataPointResponse getOnyByExternalId(@PathVariable String externalId) {
        return DataPointMapper.toResponse(getDataPointByExternalIdFeature.get(externalId));
    }

    @GetMapping
    public List<DataPointResponse> list() {
        return DataPointMapper.toResponse(findAllDataPointsFeature.find());
    }

    @PostMapping
    public DataPointResponse create(@RequestBody @Valid DataPointPayload payload) {
        return DataPointMapper.toResponse(
                createDataPointFeature.create(
                        DataPointMapper.toCreateModel(payload)
                )
        );
    }

    @PostMapping(value = "/import")
    public List<DataPointResponse> executeImport() {
        return DataPointMapper.toResponse(importDataPointsFeature.execute());
    }

    @PutMapping(value = "/{id}")
    public DataPointResponse update(
            @PathVariable Long id,
            @RequestBody @Valid DataPointPayload payload
    ) {
        return DataPointMapper.toResponse(
                updateDataPointFeature.update(
                        getDataPointByIdFeature.get(id),
                        DataPointMapper.toUpdateModel(payload)
                )
        );
    }
}
