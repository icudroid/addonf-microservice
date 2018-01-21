/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdvertiserBoTestModule } from '../../../test.module';
import { MediaTargetDetailComponent } from '../../../../../../main/webapp/app/entities/media-target/media-target-detail.component';
import { MediaTargetService } from '../../../../../../main/webapp/app/entities/media-target/media-target.service';
import { MediaTarget } from '../../../../../../main/webapp/app/entities/media-target/media-target.model';

describe('Component Tests', () => {

    describe('MediaTarget Management Detail Component', () => {
        let comp: MediaTargetDetailComponent;
        let fixture: ComponentFixture<MediaTargetDetailComponent>;
        let service: MediaTargetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [MediaTargetDetailComponent],
                providers: [
                    MediaTargetService
                ]
            })
            .overrideTemplate(MediaTargetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MediaTargetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MediaTargetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MediaTarget(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mediaTarget).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
