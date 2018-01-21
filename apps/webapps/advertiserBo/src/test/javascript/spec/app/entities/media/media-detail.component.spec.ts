/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdvertiserBoTestModule } from '../../../test.module';
import { MediaDetailComponent } from '../../../../../../main/webapp/app/entities/media/media-detail.component';
import { MediaService } from '../../../../../../main/webapp/app/entities/media/media.service';
import { Media } from '../../../../../../main/webapp/app/entities/media/media.model';

describe('Component Tests', () => {

    describe('Media Management Detail Component', () => {
        let comp: MediaDetailComponent;
        let fixture: ComponentFixture<MediaDetailComponent>;
        let service: MediaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [MediaDetailComponent],
                providers: [
                    MediaService
                ]
            })
            .overrideTemplate(MediaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MediaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MediaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Media(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.media).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
